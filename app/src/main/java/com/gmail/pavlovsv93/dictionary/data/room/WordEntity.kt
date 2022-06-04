package com.gmail.pavlovsv93.dictionary.data.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordEntity(
	@PrimaryKey
	val id: Int,

	@Embedded
	@ColumnInfo(name = "meanings")
	val meanings: List<Meaning>,

	@ColumnInfo(name = "text")
	val text: String
)

data class Meaning(
	val id: Int,
	val imageUrl: String,
	val translation: Translation
){
	inner class Translation(
		val text: String
	)
}

