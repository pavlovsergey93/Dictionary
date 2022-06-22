package com.gmail.pavlovsv93.dictionary.data.room.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteWordEntity (
	@PrimaryKey(autoGenerate = false)
	val fId : Int = 0,

	val fWord: String,

	val fTransient: String?,

	val fImageUrl: String?
)