package com.gmail.pavlovsv93.dictionary.di

import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.data.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.data.LocalDataSource
import com.gmail.pavlovsv93.dictionary.data.RemoteDataSource
import com.gmail.pavlovsv93.dictionary.data.repository.Repository
import com.gmail.pavlovsv93.dictionary.data.repository.RepositoryInterface
import com.gmail.pavlovsv93.dictionary.data.retrofit.DictionaryApi
import com.gmail.pavlovsv93.dictionary.data.retrofit.RetrofitDataSource
import com.gmail.pavlovsv93.dictionary.data.room.RoomDataSource
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import com.gmail.pavlovsv93.dictionary.presenter.PresenterInterface
import com.gmail.pavlovsv93.dictionary.presenter.mvvm.MainViewModelInterface
import com.gmail.pavlovsv93.dictionary.view.MainInteraptor
import com.gmail.pavlovsv93.dictionary.view.MainPresenter
import com.gmail.pavlovsv93.dictionary.view.MainViewModel
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"
val appModel = module {
	single<PresenterInterface> { MainPresenter(get(), get()) }
	single<CompositeDisposable> { CompositeDisposable() }
	single<InteraptorInterface<AppState>> { MainInteraptor(get(named("remote_repos")), get(named("local_repos"))) }

	single<RepositoryInterface<List<Word>>>(named("local_repos")) { Repository(get(named("local"))) }
	single<DataSourceInterface<List<Word>>>(named("local")) { LocalDataSource(get()) }
	single<RoomDataSource> { RoomDataSource() }

	single<RepositoryInterface<List<Word>>>(named("remote_repos")) { Repository(get(named("remote"))) }
	single<DataSourceInterface<List<Word>>>(named("remote")) { RemoteDataSource(get()) }
	single<RetrofitDataSource> { RetrofitDataSource(get()) }
	single<DictionaryApi> { get<Retrofit>().create(DictionaryApi::class.java) }
	single<Retrofit> {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
	viewModel { MainViewModel(get<InteraptorInterface<AppState>>(), get<CompositeDisposable>()) }
}
