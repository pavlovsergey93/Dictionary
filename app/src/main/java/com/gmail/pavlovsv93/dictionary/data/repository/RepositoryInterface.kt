package com.gmail.pavlovsv93.dictionary.data.repository

import io.reactivex.rxjava3.core.Observable

interface RepositoryInterface<T> {
	fun getDataRepository(word: String): Observable<T>
}