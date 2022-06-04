package com.gmail.pavlovsv93.dictionary.data.room

import com.gmail.pavlovsv93.dictionary.data.datasourse.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import kotlin.math.min

class RoomDataSource(private val dao: WordDao) : DataSourceInterface<List<Word>> {
	override suspend fun getData(word: String): List<Word> {
		val searchList = dao.findWord(word)
		val result: MutableList<Word> = mutableListOf()
		searchList.forEach {
			val word = Word(
				id = it.wId,
				word = it.text,
				meanings = convertMeanings(it.meanings)
			)
			result.add(word)
		}
		return result
	}

	private fun convertMeanings(meanings: WordEntity.Meaning): Word.Meanings {
		val result: MutableList<Word.Meanings> = mutableListOf()
		//meanings.forEach {
			//val meaning =
				return Word.Meanings(
				imageUrl = meanings.imageUrl,
				translation = convertTranslation(meanings.translation)
			)
		//	result.add(meaning)
		//}
		//return result
	}

	private fun convertTranslation(translation: WordEntity.Meaning.Translation): Word.Meanings.Translation? {
		return Word.Meanings.Translation(translation.translationText)
	}

	override suspend fun setDataLocal(words: List<Word>) {
		words.forEach {
			val wordCash = WordEntity(
				wId = it.id,
				text = it.word,
				meanings = convertMeamings(it.meanings)
			)
			dao.cashWord(wordCash)
		}
	}

	private fun convertMeamings(meanings: Word.Meanings): WordEntity.Meaning {
		return WordEntity.Meaning(
			mId = 0,
			imageUrl = meanings.imageUrl,
			translation = WordEntity.Meaning.Translation(meanings.translation?.text)
		)
	}
}