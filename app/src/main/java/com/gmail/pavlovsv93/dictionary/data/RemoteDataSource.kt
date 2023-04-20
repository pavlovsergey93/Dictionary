package com.gmail.pavlovsv93.dictionary.data

import com.gmail.pavlovsv93.dictionary.data.retrofit.RetrofitDataSource
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable

class RemoteDataSource(private val provider: RetrofitDataSource) : DataSourceInterface<List<Word>> {
	override fun getData(word: String): Observable<List<Word>> =
		provider.getData(word)
}