package com.gmail.pavlovsv93.dictionary.data.retrofit

import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {
	@GET("words/search")
	suspend fun search(
		@Query("search") wordToSearch: String
	): List<SearchDTOItem>
}