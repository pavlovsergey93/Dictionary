package com.gmail.pavlovsv93.dictionary.data.datasourse

import com.gmail.pavlovsv93.dictionary.data.datasourse.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.data.room.RoomDataSource
import com.gmail.pavlovsv93.dictionary.view.entityes.Word

class LocalDataSource(private val provider: RoomDataSource) : DataSourceInterface<List<Word>> {
	override suspend fun getData(word: String): List<Word> =
		provider.getData(word)

	override suspend fun setDataLocal(words: List<Word>) {
		provider.setDataLocal(words)
	}
}