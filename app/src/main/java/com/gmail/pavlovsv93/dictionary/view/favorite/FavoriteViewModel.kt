package com.gmail.pavlovsv93.dictionary.view.favorite

import androidx.lifecycle.viewModelScope
import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.presenter.FavoriteInteraptorInterface
import com.gmail.pavlovsv93.dictionary.presenter.mvvm.BaseViewModel
import com.gmail.pavlovsv93.dictionary.utils.AppDispatcher
import com.gmail.pavlovsv93.dictionary.utils.BODY_EMPTY
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(
	private val interaptor: FavoriteInteraptorInterface<AppState>,
	private val dispatcher: AppDispatcher
) : BaseViewModel<AppState>() {

	fun getFavoritesList() {
		liveData.postValue(AppState.Loading(null))
		viewModelScope.launch {
			try {
				withContext(dispatcher.io) {
					val response = interaptor.getAllFavorite()
					if (!response.isNullOrEmpty()) {
						liveData.postValue(AppState.Success(response))
					} else {
						liveData.postValue(AppState.Error(BODY_EMPTY))
					}
				}
			} catch (e: Exception) {
				liveData.postValue(AppState.Error(e.message.toString()))
			} finally {
				liveData.postValue(AppState.Loading(1))
			}
		}
	}

	fun deleteIsFavorite(idWord: Int) {
		viewModelScope.launch {
			withContext(dispatcher.io) {
				interaptor.deleteFavorite(idWord)
			}
		}
	}
}