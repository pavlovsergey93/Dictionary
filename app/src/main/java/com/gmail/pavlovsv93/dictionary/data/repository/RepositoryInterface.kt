package com.gmail.pavlovsv93.dictionary.data.repository

import com.gmail.pavlovsv93.dictionary.data.retrofit.SearchDTOItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

interface RepositoryInterface<T> {
	suspend fun getDataRepository(word: String): Response<List<SearchDTOItem>>
}