package com.gmail.pavlovsv93.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.pavlovsv93.repository.room.favorite.FavoriteWordEntity

@Database(entities = [WordEntity::class, FavoriteWordEntity::class], version = 1)
abstract class WordDB : RoomDatabase() {
	abstract fun getDao(): WordDao
}