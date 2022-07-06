package com.gmail.pavlovsv93.repository

import com.gmail.pavlovsv93.app_entities.Word

class FavoriteRepository(private val dataSource: com.gmail.pavlovsv93.repository.datasource.DataSourceInterface<List<Word>>):
	FavoriteRepositoryInterface<List<Word>> {
	override suspend fun getAllFavorite(): List<Word> {
		return dataSource.getData()
	}

	override suspend fun deleteFavorite(idWord: Int) {
		dataSource.deleteData(idWord)
	}

	override suspend fun setFavoriteData(words: List<Word>) {
		dataSource.setDataLocal(words)
	}
}