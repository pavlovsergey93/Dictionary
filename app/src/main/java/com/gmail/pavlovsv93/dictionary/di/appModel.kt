package com.gmail.pavlovsv93.dictionary.di

import com.gmail.pavlovsv93.dictionary.data.retrofit.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.data.retrofit.DictionaryApi
import com.gmail.pavlovsv93.dictionary.data.retrofit.RetrofitDataSource
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"
val appModel = module {
	single<DataSourceInterface<List<Word>>> { RetrofitDataSource(get()) }
	single<DictionaryApi> { get<Retrofit>().create(DictionaryApi::class.java) }
	single<Retrofit> { Retrofit.Builder()
		.baseUrl(BASE_URL)
		.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
		.addConverterFactory(GsonConverterFactory.create())
		.build() }
}