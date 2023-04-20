package com.gmail.pavlovsv93.dictionary.presenter

import com.gmail.pavlovsv93.app_entities.Word

interface InteraptorInterface<T> {
	suspend fun getDataInteraptor(word: String, fromRemoteSource: Boolean): List<Word>
	suspend fun setDataLocal(words: List<Word>)
	suspend fun setDataFavorite(word: Word)
}