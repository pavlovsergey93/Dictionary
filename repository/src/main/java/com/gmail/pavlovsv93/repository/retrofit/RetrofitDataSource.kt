package com.gmail.pavlovsv93.repository.retrofit

import com.gmail.pavlovsv93.dictionary.view.entityes.Word

class RetrofitDataSource(val api: DictionaryApi) :
	com.gmail.pavlovsv93.repository.datasource.DataSourceInterface<List<Word>> {
	override suspend fun getDataBySearchWord(word: String): List<Word> {
		val searchList = api.search(word)
		val result: MutableList<Word> = mutableListOf()
		searchList.forEach {
			val word = Word(
				id = it.id.toString(),
				word = it.text,
				meanings = convertMeanings(it.meanings)
			)
			result.add(word)
		}
		return result
	}


	private fun convertMeanings(meanings: List<SearchDTOItem.Meaning>): Word.Meanings {
		val result: MutableList<Word.Meanings> = mutableListOf()
		meanings.forEach {
			val meaning = Word.Meanings(
				imageUrl = "https:" + it.imageUrl,
				translation = convertTranslation(it.translation)
			)
			result.add(meaning)
		}
		return result[0]
	}

	private fun convertTranslation(translation: SearchDTOItem.Translation): Word.Meanings.Translation? {
		return Word.Meanings.Translation(translation.text)
	}

	override suspend fun setDataLocal(words: List<Word>) {
		TODO("Not yet implemented")
	}

	override suspend fun getData(): List<Word> {
		TODO("Not yet implemented")
	}

	override suspend fun deleteData(idWord: Int) {
		TODO("Not yet implemented")
	}
}