package com.gmail.pavlovsv93.dictionary.data

import com.gmail.pavlovsv93.dictionary.view.entityes.Word

sealed class AppState {
	data class Success(val data: List<Word>) : AppState()
	data class Error(val error: Throwable) : AppState()
	data class Loading(val progress: Int?) : AppState()
}
