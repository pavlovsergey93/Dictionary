package com.gmail.pavlovsv93.dictionary.data.repository

import com.gmail.pavlovsv93.dictionary.data.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.data.retrofit.SearchDTOItem
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class Repository(private val dataSource: DataSourceInterface<List<Word>>) :
	RepositoryInterface<List<Word>> {
	override suspend fun getDataRepository(word: String): Response<List<SearchDTOItem>> =
		dataSource.getData(word)
}