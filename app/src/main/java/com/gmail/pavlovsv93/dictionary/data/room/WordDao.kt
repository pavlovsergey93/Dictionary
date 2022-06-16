package com.gmail.pavlovsv93.dictionary.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {
	@Insert (onConflict = OnConflictStrategy.REPLACE)
	suspend fun cashWord(vararg word: WordEntity)

	@Query("SELECT * FROM WordEntity")
	suspend fun getAllWord(): List<WordEntity>

	@Query("SELECT * FROM WordEntity WHERE text LIKE '%' || :word || '%'")
	suspend fun findWord(word: String): List<WordEntity>
}