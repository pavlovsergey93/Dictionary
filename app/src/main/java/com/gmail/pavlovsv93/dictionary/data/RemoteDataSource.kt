package com.gmail.pavlovsv93.dictionary.data

import com.gmail.pavlovsv93.dictionary.data.retrofit.RetrofitDataSource
import com.gmail.pavlovsv93.dictionary.data.retrofit.SearchDTOItem
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class RemoteDataSource(private val provider: RetrofitDataSource) : DataSourceInterface<List<Word>> {
	override suspend fun getData(word: String): Response<List<SearchDTOItem>> =
		provider.getData(word)
}