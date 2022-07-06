package com.gmail.pavlovsv93.repository

interface FavoriteRepositoryInterface <T> {
	suspend fun getAllFavorite(): T
	suspend fun setFavoriteData(words: T)
	suspend fun deleteFavorite(idWord: Int)
}