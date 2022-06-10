package com.gmail.pavlovsv93.dictionary.data.room

import com.gmail.pavlovsv93.dictionary.data.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.data.retrofit.SearchDTOItem
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class RoomDataSource: DataSourceInterface<List<Word>> {
	override suspend fun getData(word: String): Response<List<SearchDTOItem>> {
		TODO("Not yet implemented")
		// задел на будущее
	}
}