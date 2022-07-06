package com.gmail.pavlovsv93.repository.room.favorite

import com.gmail.pavlovsv93.app_entities.Word
import com.gmail.pavlovsv93.repository.room.WordDao

class RoomFavoriteDataSourse(private val dao: WordDao) :
	com.gmail.pavlovsv93.repository.datasource.DataSourceInterface<List<Word>> {
	override suspend fun getDataBySearchWord(word: String): List<Word> {
		val result: MutableList<Word> = mutableListOf()
		dao.findFavoriteWord(word).forEach {
			val word = Word(
				id = it.fId.toString(),
				word = it.fWord,
				meanings = convertMeanings(it.fTransient, it.fImageUrl)
			)
			result.add(word)
		}
		return result
	}

	override suspend fun setDataLocal(words: List<Word>) {
		val word = FavoriteWordEntity(
			fId = words[0].id.toInt(),
			fWord = words[0].word,
			fTransient = words[0].meanings.translation?.text,
			fImageUrl = words[0].meanings.imageUrl
		)
		if (words[0].isFavorite) {
			dao.addToFavorite(word)
		} else {
			dao.deleteFavorite(word)
		}
	}

	override suspend fun getData(): List<Word> {
		val result: MutableList<Word> = mutableListOf()
		dao.getAllFavorite().forEach {
			val word = Word(
				id = it.fId.toString(),
				word = it.fWord,
				meanings = convertMeanings(it.fTransient, it.fImageUrl)
			)
			result.add(word)
		}
		return result
	}

	private fun convertMeanings(translation: String?, fImageUrl: String?): Word.Meanings {
		return Word.Meanings(
			imageUrl = "https:$fImageUrl",
			translation = Word.Meanings.Translation(translation)
		)
	}

	override suspend fun deleteData(idWord: Int) {
		val word = dao.getWord(idWord)
		dao.deleteFavorite(word)
	}
}