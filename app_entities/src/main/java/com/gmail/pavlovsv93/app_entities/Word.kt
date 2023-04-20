package com.gmail.pavlovsv93.app_entities

data class Word(
	val id: String,
	val word: String,
	val meanings: Meanings,
	var isFavorite: Boolean = false
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