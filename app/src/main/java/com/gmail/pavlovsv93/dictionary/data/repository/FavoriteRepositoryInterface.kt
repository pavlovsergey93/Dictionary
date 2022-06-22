package com.gmail.pavlovsv93.dictionary.data.repository

import com.gmail.pavlovsv93.dictionary.view.entityes.Word

interface FavoriteRepositoryInterface <T> {
	suspend fun getAllFavorite(): T
	suspend fun setFavoriteData(words: T)
	suspend fun deleteFavorite(idWord: Int)
}