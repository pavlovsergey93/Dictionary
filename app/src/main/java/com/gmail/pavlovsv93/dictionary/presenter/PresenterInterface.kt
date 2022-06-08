package com.gmail.pavlovsv93.dictionary.presenter

import com.gmail.pavlovsv93.dictionary.view.ViewInterface

interface PresenterInterface {
	fun onAttach(view: ViewInterface)
	fun onDetach(view: ViewInterface)
	fun getDataPresenter(word: String, isOnline: Boolean)
}