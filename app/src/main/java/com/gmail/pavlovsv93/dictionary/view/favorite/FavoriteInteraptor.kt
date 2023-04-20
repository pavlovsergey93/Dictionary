package com.gmail.pavlovsv93.dictionary.view.favorite

import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.repository.FavoriteRepositoryInterface
import com.gmail.pavlovsv93.dictionary.presenter.FavoriteInteraptorInterface
import com.gmail.pavlovsv93.app_entities.Word

class FavoriteInteraptor(private val favoriteRepository: FavoriteRepositoryInterface<List<Word>>) : FavoriteInteraptorInterface<AppState> {
	override suspend fun getAllFavorite(): List<Word> {
		return favoriteRepository.getAllFavorite()
	}

	override suspend fun deleteFavorite(idWord: Int) {
		favoriteRepository.deleteFavorite(idWord)
	}

	override suspend fun setFavoriteData(word: Word) {
		TODO("Not yet implemented")
	}
}