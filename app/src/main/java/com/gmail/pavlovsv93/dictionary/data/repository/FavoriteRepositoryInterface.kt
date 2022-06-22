package com.gmail.pavlovsv93.dictionary.data.repository

interface FavoriteRepositoryInterface <T> {
	suspend fun getAllFavorite(): T
	suspend fun setFavoriteData(words: T)
	suspend fun deleteFavorite(idWord: Int)
}