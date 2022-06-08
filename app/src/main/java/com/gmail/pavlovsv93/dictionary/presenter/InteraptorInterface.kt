package com.gmail.pavlovsv93.dictionary.presenter

import io.reactivex.rxjava3.core.Observable

interface InteraptorInterface<T> {
	fun getDataInteraptor(word: String, fromRemoteSource: Boolean): Observable<T>
}