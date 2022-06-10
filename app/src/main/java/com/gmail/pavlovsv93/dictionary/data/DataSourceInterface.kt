package com.gmail.pavlovsv93.dictionary.data

import com.gmail.pavlovsv93.dictionary.data.retrofit.SearchDTOItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

interface DataSourceInterface<T> {
	suspend fun getData(word:String): Response<List<SearchDTOItem>>
}