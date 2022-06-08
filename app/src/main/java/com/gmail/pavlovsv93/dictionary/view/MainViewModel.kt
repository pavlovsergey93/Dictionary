package com.gmail.pavlovsv93.dictionary.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import com.gmail.pavlovsv93.dictionary.presenter.mvvm.MainViewModelInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(
	private val interaptor: InteraptorInterface<AppState>,
	private val compositeDisposable: CompositeDisposable
) : ViewModel(), MainViewModelInterface {
	private val liveData: MutableLiveData<AppState> = MutableLiveData()
	override fun getLiveData(): LiveData<AppState> = liveData

	override fun getDataViewModel(word: String, isOnline: Boolean) {
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
	}
}