package com.gmail.pavlovsv93.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

class AppDispatcher {
	val main: MainCoroutineDispatcher = Dispatchers.Main
	val io: CoroutineDispatcher = Dispatchers.IO
	val default: CoroutineDispatcher = Dispatchers.Default
	val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}