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
	private var job: Job? = null
	private var jobSetRoom: Job? = null
	override fun getDataViewModel(word: String, isOnline: Boolean): LiveData<AppState> {
		liveData.postValue(AppState.Loading(null))
		if (job?.isActive == true) {
			job?.cancel()
		}
		job = scope.launch() {
			try {
				withContext(dispatcher.io) {
					interaptor.getDataInteraptor(word, isOnline).let { result ->
						withContext(dispatcher.main) {
							liveData.postValue(AppState.Loading(1))
						}
						if (!result.isNullOrEmpty()) {
							withContext(dispatcher.io) {
								liveData.postValue(AppState.Success(result))
								interaptor.setDataLocal(result)
							}
						} else if (result.isEmpty()) {
							liveData.postValue(AppState.Error(BODY_EMPTY))
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

	fun setFavorite(word: Word) {
		jobSetRoom = scope
			.launch {
			withContext(dispatcher.io) {
				interaptor.setDataFavorite(word)
			}
		}
	}

	override fun onCleared() {
		scope.cancel()
		super.onCleared()
	}
}