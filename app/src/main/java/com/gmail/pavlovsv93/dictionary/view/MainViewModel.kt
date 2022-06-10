package com.gmail.pavlovsv93.dictionary.view

import androidx.lifecycle.LiveData
import com.gmail.pavlovsv93.dictionary.App
import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.data.retrofit.SearchDTOItem
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import com.gmail.pavlovsv93.dictionary.presenter.mvvm.BaseViewModel
import com.gmail.pavlovsv93.dictionary.utils.AppDispatcher
import com.gmail.pavlovsv93.dictionary.utils.BODY_EMPTY
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*

class MainViewModel
constructor(
	private val interaptor: InteraptorInterface<AppState>,
	private val scope: CoroutineScope,
	private val dispatcher: AppDispatcher
) : BaseViewModel<AppState>() {
	private var job : Job? = null
	override fun getDataViewModel(word: String, isOnline: Boolean): LiveData<AppState> {
		liveData.postValue(AppState.Loading(null))
		if(job?.isActive == true){
			job?.cancel()
		}
		job = scope.launch() {
			try {
				withContext(dispatcher.io) {
					interaptor.getDataInteraptor(word, isOnline).let { result ->
						withContext(dispatcher.main) {
							liveData.postValue(AppState.Loading( 1))
						}
						if (result.isSuccessful && result.body() != null) {
							val convertList = convertToWord(result.body()!!)
							liveData.postValue(AppState.Success(convertList))
						} else if (result.body()?.isEmpty() == true) {
							liveData.postValue(AppState.Error(BODY_EMPTY))
						} else {
							liveData.postValue(AppState.Error(result.message()))
						}
					}
				}
			} catch (e: Exception) {
				liveData.postValue(AppState.Error(e.message.toString()))
				liveData.postValue(AppState.Loading(e.message?.length))
			}
		}
		return super.getDataViewModel(word, isOnline)
	}

	override fun onCleared() {
		scope.cancel()
		super.onCleared()
	}

	private fun convertToWord(list: List<SearchDTOItem>): List<Word> {
		val convertList: MutableList<Word> = mutableListOf()
		list.forEach {
			convertList.add(
				Word(
					word = it.text,
					meanings = convertMeanings(it.meanings)

				)
			)
		}
		return convertList
	}

	private fun convertMeanings(meanings: List<SearchDTOItem.Meaning>): List<Word.Meanings> {
		val convertList: MutableList<Word.Meanings> = mutableListOf()
		meanings.forEach {
			convertList.add(
				Word.Meanings(
					translation = convertTranslation(it.translation),
					imageUrl = it.imageUrl
				)
			)
		}
		return convertList
	}

	private fun convertTranslation(translation: SearchDTOItem.Translation): Word.Translation? {
		return Word.Translation(translation.text)
	}
}