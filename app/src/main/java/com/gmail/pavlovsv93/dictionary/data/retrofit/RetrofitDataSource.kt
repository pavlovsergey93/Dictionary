package com.gmail.pavlovsv93.dictionary.data.retrofit

import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable

class RetrofitDataSource(val api: DictionaryApi) : DataSourceInterface<List<Word>> {
	override fun getData(word: String): Observable<List<Word>> {
		return api.search(word)
			.map { convert(it) }
	}

	private fun convert(listData: List<SearchDTOItem>): List<Word> {
		val list: MutableList<Word> = mutableListOf()
		listData.forEach {
			val word = Word(
				word = it.text,
				meanings = convertMeanings(it.meanings)
			)
			list.add(word)
		}
		return list
	}

	private fun convertMeanings(meaningsData: List<SearchDTOItem.Meaning>): List<Word.Meanings> {
		val listMeaning : MutableList<Word.Meanings> = mutableListOf()
		meaningsData.forEach {
			val meanings = Word.Meanings(
				imageUrl = it.imageUrl,
				translation = convertTranslation(it.translation)
			)
			listMeaning.add(meanings)
		}
		return listMeaning
	}

	private fun convertTranslation(translation: SearchDTOItem.Translation): Word.Translation? =
		Word.Translation(text = translation.text)
}