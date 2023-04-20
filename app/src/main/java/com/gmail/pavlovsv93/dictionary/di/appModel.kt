package com.gmail.pavlovsv93.dictionary.di

import androidx.room.Room
import com.gmail.pavlovsv93.app_entities.Word
import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.presenter.FavoriteInteraptorInterface
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import com.gmail.pavlovsv93.dictionary.view.MainInteraptor
import com.gmail.pavlovsv93.dictionary.view.MainViewModel
import com.gmail.pavlovsv93.dictionary.view.favorite.FavoriteInteraptor
import com.gmail.pavlovsv93.dictionary.view.favorite.FavoriteViewModel
import com.gmail.pavlovsv93.repository.FavoriteRepository
import com.gmail.pavlovsv93.repository.RepositoryInterface
import com.gmail.pavlovsv93.repository.datasource.DataSourceInterface
import com.gmail.pavlovsv93.repository.datasource.LocalDataSource
import com.gmail.pavlovsv93.repository.retrofit.DictionaryApi
import com.gmail.pavlovsv93.repository.retrofit.RetrofitDataSource
import com.gmail.pavlovsv93.repository.room.RoomDataSource
import com.gmail.pavlovsv93.repository.room.WordDB
import com.gmail.pavlovsv93.repository.room.WordDao
import com.gmail.pavlovsv93.repository.room.favorite.RoomFavoriteDataSourse
import com.gmail.pavlovsv93.utils.AppDispatcher
import com.gmail.pavlovsv93.utils.FAVORITE_VIEWMODEL
import com.gmail.pavlovsv93.utils.MAIN_VIEWMODEL
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"
const val NAME_DATABASE = "DictionaryWordsDB"
const val REMOTE_REPOS = "remote_repos"
const val LOCAL_REPOS = "local_repos"
const val FAVORITE_REPOS = "favorite_repos"
const val REMOTE_DS = "remote"
const val LOCAL_DS = "local"
val appModel = module {
	single<CompositeDisposable> { CompositeDisposable() }
	single<InteraptorInterface<AppState>> {
		MainInteraptor(
			remoteRepository = get(named(REMOTE_REPOS)),
			localRepository = get(named(LOCAL_REPOS)),
			favoriteRepository = get()
		)
	}

	single<RepositoryInterface<List<Word>>>(named(LOCAL_REPOS)) {
		com.gmail.pavlovsv93.repository.Repository(
			dataSource = get(
				named(LOCAL_DS)
			)
		)
	}
	single<DataSourceInterface<List<Word>>>(named(LOCAL_DS)) {
		LocalDataSource(
			provider = get()
		)
	}
	single<RoomDataSource> {
		RoomDataSource(
			dao = get()
		)
	}
	single<WordDB> {
		Room.databaseBuilder(androidContext(), WordDB::class.java, NAME_DATABASE).build()
	}
	single<WordDao> { get<WordDB>().getDao() }

	single<RepositoryInterface<List<Word>>>(named(REMOTE_REPOS)) {
		com.gmail.pavlovsv93.repository.Repository(
			dataSource = get(
				named(REMOTE_DS)
			)
		)
	}
	single<DataSourceInterface<List<Word>>>(
		named(REMOTE_DS)
	) {
		com.gmail.pavlovsv93.repository.datasource.RemoteDataSource(
			provider = get()
		)
	}
	single<RetrofitDataSource> {
		RetrofitDataSource(
			api = get()
		)
	}
	single<DictionaryApi> { get<Retrofit>().create(
		DictionaryApi::class.java) }
	single<Retrofit> {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
	factory<AppDispatcher> { AppDispatcher() }
	factory<CoroutineScope> { CoroutineScope(get<AppDispatcher>().default) }
	viewModel(named(MAIN_VIEWMODEL)) {
		MainViewModel(
			interaptor = get(),
			scope = get(),
			dispatcher = get()
		)
	}

	single { RoomFavoriteDataSourse(dao = get()) }
	single<DataSourceInterface<List<Word>>>(
		named(FAVORITE_REPOS)
	) {
		com.gmail.pavlovsv93.repository.datasource.FavoriteDataSource(
			provider = get()
		)
	}
	single<com.gmail.pavlovsv93.repository.FavoriteRepositoryInterface<List<Word>>> {
		FavoriteRepository(
			dataSource = get(named(FAVORITE_REPOS))
		)
	}
	single<FavoriteInteraptorInterface<AppState>> { FavoriteInteraptor(favoriteRepository = get()) }
	viewModel(named(FAVORITE_VIEWMODEL)) {
		FavoriteViewModel(
			interaptor = get(),
			dispatcher = get()
		)
	}
}
