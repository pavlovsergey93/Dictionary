package com.gmail.pavlovsv93.dictionary.data.datasourse

import com.gmail.pavlovsv93.dictionary.data.room.favorite.RoomFavoriteDataSourse
import com.gmail.pavlovsv93.dictionary.view.entityes.Word

class FavoriteDataSource(private val provider: RoomFavoriteDataSourse) :
	DataSourceInterface<List<Word>> {
	override suspend fun getDataBySearchWord(word: String): List<Word> {
		return provider.getDataBySearchWord(word)
	}

	override suspend fun getData(): List<Word> {
		return provider.getData()
	}

	override suspend fun setDataLocal(words: List<Word>) {
		provider.setDataLocal(words)
	}

	override suspend fun deleteData(idWord: Int) {
		provider.deleteData(idWord)
	}
}