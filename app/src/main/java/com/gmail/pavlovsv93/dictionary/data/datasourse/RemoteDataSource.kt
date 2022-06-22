package com.gmail.pavlovsv93.dictionary.data.datasourse

import com.gmail.pavlovsv93.dictionary.data.retrofit.RetrofitDataSource
import com.gmail.pavlovsv93.dictionary.view.entityes.Word

class RemoteDataSource(private val provider: RetrofitDataSource) : DataSourceInterface<List<Word>> {
	override suspend fun getDataBySearchWord(word: String): List<Word> =
		provider.getDataBySearchWord(word)

	override suspend fun setDataLocal(words: List<Word>) {
		TODO("Not yet implemented")
		// не описывать
	}

	override suspend fun getData(): List<Word> {
		TODO("Not yet implemented")
		// не описывать
	}

	override suspend fun deleteData(idWord: Int) {
		TODO("Not yet implemented")
		// не описывать
	}
}