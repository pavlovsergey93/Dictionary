package com.gmail.pavlovsv93.dictionary

import android.app.Application
import com.gmail.pavlovsv93.dictionary.di.appModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App: Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin{
			androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
			androidContext(this@App)
			modules(appModel)
		}
	}
}