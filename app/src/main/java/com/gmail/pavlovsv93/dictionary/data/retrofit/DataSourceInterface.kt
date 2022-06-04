package com.gmail.pavlovsv93.dictionary.data.retrofit

import io.reactivex.rxjava3.core.Observable

interface DataSourceInterface<T> {
	fun getData(word:String): Observable<T>
}