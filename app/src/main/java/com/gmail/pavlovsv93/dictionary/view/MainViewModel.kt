package com.gmail.pavlovsv93.dictionary.view

import androidx.lifecycle.LiveData
import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import com.gmail.pavlovsv93.dictionary.presenter.mvvm.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val interaptor: InteraptorInterface<AppState>
) : BaseViewModel<AppState>()
{
	override fun getDataViewModel(word: String, isOnline: Boolean): LiveData<AppState> {
		compositeDisposable.add(
			interaptor.getDataInteraptor(word, isOnline)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnSubscribe { liveData.postValue(AppState.Loading(null)) }
				.subscribeBy(
					onNext = { state ->
						liveData.postValue(state)
					},
					onError = { error ->
						liveData.postValue(AppState.Error(error))
					},
					onComplete = {liveData.postValue(AppState.Loading(1))}
				)
		)
		return super.getDataViewModel(word, isOnline)
	}

	override fun onCleared() {
		compositeDisposable.dispose()
		super.onCleared()
	}
}