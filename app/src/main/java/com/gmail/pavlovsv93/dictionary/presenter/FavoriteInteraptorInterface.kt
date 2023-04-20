package com.gmail.pavlovsv93.dictionary.presenter

import com.gmail.pavlovsv93.app_entities.Word

interface FavoriteInteraptorInterface<T> {
	suspend fun getAllFavorite():List<Word>
	suspend fun setFavoriteData(word: Word)
	suspend fun deleteFavorite(idWord: Int)
}