package com.gmail.pavlovsv93.repository

import com.gmail.pavlovsv93.app_entities.Word
import com.gmail.pavlovsv93.repository.datasource.DataSourceInterface
import com.gmail.pavlovsv93.repository.datasource.FavoriteDataSource
import com.gmail.pavlovsv93.repository.datasource.LocalDataSource

class Repository(private val dataSource: DataSourceInterface<List<Word>>) :
	RepositoryInterface<List<Word>> {
	override suspend fun getDataRepository(word: String): List<Word> =
		dataSource.getDataBySearchWord(word)

	override suspend fun setDataLocal(words: List<Word>) {
		(dataSource as LocalDataSource).setDataLocal(words)
	}

	override suspend fun setDataFavorite(word: Word) {
		(dataSource as FavoriteDataSource).setDataLocal(listOf(word))
	}
	override suspend fun getDataFavorite() : List<Word> {
		return (dataSource as FavoriteDataSource).getData()
	}
}