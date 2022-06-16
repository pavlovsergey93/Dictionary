package com.gmail.pavlovsv93.dictionary.data.datasourse

interface DataSourceInterface<T> {
	suspend fun getData(word:String): T
}