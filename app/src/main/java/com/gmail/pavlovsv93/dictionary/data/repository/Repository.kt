package com.gmail.pavlovsv93.dictionary.data.repository

import com.gmail.pavlovsv93.dictionary.data.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable

class Repository(private val dataSource: DataSourceInterface<List<Word>>) :
	RepositoryInterface<List<Word>> {
	override fun getDataRepository(word: String): Observable<List<Word>> =
		dataSource.getData(word)
}