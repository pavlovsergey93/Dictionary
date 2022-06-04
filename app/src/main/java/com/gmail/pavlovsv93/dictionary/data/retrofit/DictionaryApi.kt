package com.gmail.pavlovsv93.dictionary.data.retrofit

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {
	@GET("words/search")
	fun search(
		@Query("search") wordToSearch: String
	): Observable<List<SearchDTOItem>>
}