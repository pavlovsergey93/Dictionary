package com.gmail.pavlovsv93.repository

import com.gmail.pavlovsv93.app_entities.Word

class Repository(private val dataSource: com.gmail.pavlovsv93.repository.datasource.DataSourceInterface<List<Word>>) :
	RepositoryInterface<List<Word>> {
	override suspend fun getDataRepository(word: String): List<Word> =
		dataSource.getDataBySearchWord(word)

	override suspend fun setDataLocal(words: List<Word>) {
		(dataSource as com.gmail.pavlovsv93.repository.datasource.LocalDataSource).setDataLocal(words)
	}

	override suspend fun setDataFavorite(word: Word) {
		(dataSource as com.gmail.pavlovsv93.repository.datasource.FavoriteDataSource).setDataLocal(listOf(word))
	}
	override suspend fun getDataFavorite() : List<Word> {
		return (dataSource as com.gmail.pavlovsv93.repository.datasource.FavoriteDataSource).getData()
	}
}