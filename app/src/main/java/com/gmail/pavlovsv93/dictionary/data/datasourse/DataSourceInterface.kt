package com.gmail.pavlovsv93.dictionary.data.datasourse

import com.gmail.pavlovsv93.dictionary.view.entityes.Word

interface DataSourceInterface<T> {
	suspend fun getData(word:String): T
	suspend fun setDataLocal(words: T)
}