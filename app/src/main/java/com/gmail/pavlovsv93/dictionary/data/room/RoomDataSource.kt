package com.gmail.pavlovsv93.dictionary.data.room

import com.gmail.pavlovsv93.dictionary.data.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable

class RoomDataSource: DataSourceInterface<List<Word>> {
	override fun getData(word: String): Observable<List<Word>> {
		TODO("Not yet implemented")
		// задел на будущее
	}
}