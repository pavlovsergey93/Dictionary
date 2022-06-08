package com.gmail.pavlovsv93.dictionary.data

import com.gmail.pavlovsv93.dictionary.data.room.RoomDataSource
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable

class LocalDataSource(private val provider: RoomDataSource) : DataSourceInterface<List<Word>> {
	override fun getData(word: String): Observable<List<Word>> =
		provider.getData(word)
}