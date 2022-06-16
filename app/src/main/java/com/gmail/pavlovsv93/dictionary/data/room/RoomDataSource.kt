package com.gmail.pavlovsv93.dictionary.data.room

import com.gmail.pavlovsv93.dictionary.data.datasourse.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.view.entityes.Word

class RoomDataSource(private val dao: WordDao) : DataSourceInterface<List<Word>> {
	override suspend fun getData(word: String): List<Word> {
		val searchList = dao.findWord(word)
		val result: MutableList<Word> = mutableListOf()
		searchList.forEach {
			val word = Word(
				id = it.id,
				word = it.text,
				meanings = convertMeanings(it.meanings)
			)
			result.add(word)
		}
		return result
	}

	private fun convertMeanings(meanings: List<Meaning>): List<Word.Meanings> {
		val result: MutableList<Word.Meanings> = mutableListOf()
		meanings.forEach {
			val meaning = Word.Meanings(
				imageUrl = it.imageUrl,
				translation = convertTranslation(it.translation)
			)
			result.add(meaning)
		}
		return result
	}

	private fun convertTranslation(translation: Word.Meanings.Translation): Word.Meanings.Translation? {
		return Word.Meanings.Translation(translation.text)
	}
}