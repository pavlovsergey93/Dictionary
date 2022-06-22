package com.gmail.pavlovsv93.dictionary.data.repository

import com.gmail.pavlovsv93.dictionary.data.datasourse.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.view.entityes.Word

class FavoriteRepository(private val dataSource: DataSourceInterface<List<Word>>): FavoriteRepositoryInterface <List<Word>> {
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