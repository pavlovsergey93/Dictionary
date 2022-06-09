package com.gmail.pavlovsv93.dictionary.presenter.mvvm

import androidx.lifecycle.LiveData
import com.gmail.pavlovsv93.dictionary.data.AppState

interface MainViewModelInterface {
	fun getDataViewModel(word: String, isOnline: Boolean)
	fun getLiveData() : LiveData<AppState>
}