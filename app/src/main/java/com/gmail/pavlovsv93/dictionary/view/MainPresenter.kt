package com.gmail.pavlovsv93.dictionary.view

import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import com.gmail.pavlovsv93.dictionary.presenter.PresenterInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter(
	private val interaptor: InteraptorInterface<AppState>,
	private val compositeDisposable: CompositeDisposable
) : PresenterInterface {

	private var currentView: ViewInterface? = null

	override fun onAttach(view: ViewInterface) {
		if (currentView != view) {
			currentView = view
		}
	}

	override fun onDetach(view: ViewInterface) {
		if (currentView == view) {
			currentView = null
		}
		compositeDisposable.dispose()
	}

	override fun getDataPresenter(word: String, isOnline: Boolean) {
		compositeDisposable.add(
			interaptor.getDataInteraptor(word, isOnline)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnSubscribe { currentView?.rangeData(AppState.Loading(null)) }
				.subscribeWith(getObserver())
		)
	}

	private fun getObserver(): DisposableObserver<AppState> {
		return object : DisposableObserver<AppState>() {
			override fun onNext(t: AppState) {
				currentView?.rangeData(t)
				currentView?.rangeData(AppState.Loading(1))
			}

			override fun onError(e: Throwable) {
				currentView?.rangeData(AppState.Error(e))
				currentView?.rangeData(AppState.Loading(1))
			}

			override fun onComplete() {
				currentView?.rangeData(AppState.Loading(1))
			}

		}
	}

}