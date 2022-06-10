package com.gmail.pavlovsv93.dictionary.presenter

import com.gmail.pavlovsv93.dictionary.data.retrofit.SearchDTOItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

interface InteraptorInterface<T> {
	suspend fun getDataInteraptor(word: String, fromRemoteSource: Boolean): Response<List<SearchDTOItem>>
}