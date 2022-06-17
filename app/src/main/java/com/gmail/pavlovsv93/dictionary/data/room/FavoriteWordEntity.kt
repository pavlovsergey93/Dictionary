package com.gmail.pavlovsv93.dictionary.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteWordEntity (
	@PrimaryKey(autoGenerate = true)
	val fId : Int = 0,

	val fWord: String,

	val fTransient: String,

	val fImageUrl: String
)