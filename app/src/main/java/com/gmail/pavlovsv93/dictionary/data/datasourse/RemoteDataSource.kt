package com.gmail.pavlovsv93.dictionary.data.datasourse

import com.gmail.pavlovsv93.dictionary.data.datasourse.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.data.retrofit.RetrofitDataSource
import com.gmail.pavlovsv93.dictionary.view.entityes.Word

class RemoteDataSource(private val provider: RetrofitDataSource) : DataSourceInterface<List<Word>> {
	override suspend fun getData(word: String): List<Word> =
		provider.getData(word)

	override suspend fun setDataLocal(words: List<Word>) {
		TODO("Not yet implemented")
		// не описывать
	}
}