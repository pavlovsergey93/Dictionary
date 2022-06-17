package com.gmail.pavlovsv93.dictionary.view.entityes

import com.google.gson.annotations.SerializedName

data class Word(
	val id: String,
	val word: String,
	val meanings: Meanings
) {
	data class Meanings(
		val translation: Translation?,
		val imageUrl: String?
	) {

		data class Translation(
			val text: String?
		)
	}
}