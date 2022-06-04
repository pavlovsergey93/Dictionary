package com.gmail.pavlovsv93.dictionary.data.repository

import com.gmail.pavlovsv93.dictionary.data.retrofit.SearchDTOItem
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

interface RepositoryInterface<T> {
	suspend fun getDataRepository(word: String): List<Word>
	suspend fun setDataLocal(words: List<Word>)
}