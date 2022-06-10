package com.gmail.pavlovsv93.dictionary.data

import com.gmail.pavlovsv93.dictionary.data.retrofit.SearchDTOItem
import com.gmail.pavlovsv93.dictionary.data.room.RoomDataSource
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class LocalDataSource(private val provider: RoomDataSource) : DataSourceInterface<List<Word>> {
	override suspend fun getData(word: String): Response<List<SearchDTOItem>> =
		provider.getData(word)
}