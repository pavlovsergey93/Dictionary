package com.gmail.pavlovsv93.dictionary.data.retrofit

import com.gmail.pavlovsv93.dictionary.data.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class RetrofitDataSource(val api: DictionaryApi) : DataSourceInterface<List<Word>> {
	override suspend fun getData(word: String): Response<List<SearchDTOItem>> {
		return api.search(word)
	}
}