package com.gmail.pavlovsv93.dictionary.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class], version = 1)
abstract class WordDB : RoomDatabase() {
	abstract fun getDao(): WordDao
}