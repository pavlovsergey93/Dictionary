package com.gmail.pavlovsv93.dictionary.data.repository

import com.gmail.pavlovsv93.dictionary.data.datasourse.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.view.entityes.Word

class Repository(private val dataSource: DataSourceInterface<List<Word>>) :
	RepositoryInterface<List<Word>> {
	override suspend fun getDataRepository(word: String): List<Word> =
		dataSource.getData(word)
}