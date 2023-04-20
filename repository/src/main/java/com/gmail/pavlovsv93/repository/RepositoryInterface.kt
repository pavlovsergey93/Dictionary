package com.gmail.pavlovsv93.repository

import com.gmail.pavlovsv93.app_entities.Word

interface RepositoryInterface<T> {
	suspend fun getDataRepository(word: String): List<Word>
	suspend fun setDataLocal(words: List<Word>)
	suspend fun setDataFavorite(words: Word)
	suspend fun getDataFavorite(): List<Word>
}