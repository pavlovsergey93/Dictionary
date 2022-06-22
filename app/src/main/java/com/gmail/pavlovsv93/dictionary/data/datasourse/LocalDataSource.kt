package com.gmail.pavlovsv93.dictionary.data.datasourse

import com.gmail.pavlovsv93.dictionary.data.room.RoomDataSource
import com.gmail.pavlovsv93.dictionary.view.entityes.Word

class LocalDataSource(private val provider: RoomDataSource) : DataSourceInterface<List<Word>> {
	override suspend fun getDataBySearchWord(word: String): List<Word> =
		provider.getDataBySearchWord(word)

	override suspend fun setDataLocal(words: List<Word>) {
		provider.setDataLocal(words)
	}

	override suspend fun getData(): List<Word> {
		return provider.getData()
	}

	override suspend fun deleteData(idWord: Int) {
		TODO("Not yet implemented")
	}
}