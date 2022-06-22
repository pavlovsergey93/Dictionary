package com.gmail.pavlovsv93.dictionary.di

import androidx.room.Room
import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.data.datasourse.DataSourceInterface
import com.gmail.pavlovsv93.dictionary.data.datasourse.FavoriteDataSource
import com.gmail.pavlovsv93.dictionary.data.datasourse.LocalDataSource
import com.gmail.pavlovsv93.dictionary.data.datasourse.RemoteDataSource
import com.gmail.pavlovsv93.dictionary.data.repository.FavoriteRepository
import com.gmail.pavlovsv93.dictionary.data.repository.FavoriteRepositoryInterface
import com.gmail.pavlovsv93.dictionary.data.repository.Repository
import com.gmail.pavlovsv93.dictionary.data.repository.RepositoryInterface
import com.gmail.pavlovsv93.dictionary.data.retrofit.DictionaryApi
import com.gmail.pavlovsv93.dictionary.data.retrofit.RetrofitDataSource
import com.gmail.pavlovsv93.dictionary.data.room.RoomDataSource
import com.gmail.pavlovsv93.dictionary.data.room.WordDB
import com.gmail.pavlovsv93.dictionary.data.room.WordDao
import com.gmail.pavlovsv93.dictionary.data.room.favorite.RoomFavoriteDataSourse
import com.gmail.pavlovsv93.dictionary.presenter.FavoriteInteraptorInterface
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import com.gmail.pavlovsv93.dictionary.utils.AppDispatcher
import com.gmail.pavlovsv93.dictionary.utils.FAVORITE_VIEWMODEL
import com.gmail.pavlovsv93.dictionary.utils.MAIN_VIEWMODEL
import com.gmail.pavlovsv93.dictionary.view.MainInteraptor
import com.gmail.pavlovsv93.dictionary.view.MainViewModel
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import com.gmail.pavlovsv93.dictionary.view.favorite.FavoriteInteraptor
import com.gmail.pavlovsv93.dictionary.view.favorite.FavoriteViewModel
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
val appModel = module {
	single<CompositeDisposable> { CompositeDisposable() }
	single<InteraptorInterface<AppState>> {
		MainInteraptor(
			remoteRepository = get(named("remote_repos")),
			localRepository = get(named("local_repos")),
			favoriteRepository = get()
		)
	}

	single<RepositoryInterface<List<Word>>>(named("local_repos")) {
		Repository(
			dataSource = get(
				named("local")
			)
		)
	}
	single<DataSourceInterface<List<Word>>>(named("local")) { LocalDataSource(provider = get()) }
	single<RoomDataSource> { RoomDataSource(dao = get()) }
	single<WordDB> { Room.databaseBuilder(androidContext(), WordDB::class.java, NAME_DATABASE).build() }
	single<WordDao> { get<WordDB>().getDao() }

	single<RepositoryInterface<List<Word>>>(named("remote_repos")) {
		Repository(
			dataSource = get(
				named("remote")
			)
		)
	}
	single<DataSourceInterface<List<Word>>>(named("remote")) { RemoteDataSource(provider = get()) }
	single<RetrofitDataSource> { RetrofitDataSource(api = get()) }
	single<DictionaryApi> { get<Retrofit>().create(DictionaryApi::class.java) }
	single<Retrofit> {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
	factory<AppDispatcher> { AppDispatcher() }
	factory<CoroutineScope> { CoroutineScope(get<AppDispatcher>().default) }
	viewModel(named(MAIN_VIEWMODEL)) { MainViewModel(interaptor = get(), scope = get(), dispatcher = get()) }

	single { RoomFavoriteDataSourse(dao = get()) }
	single<DataSourceInterface<List<Word>>>(named("favorite")) { FavoriteDataSource(provider = get()) }
	single<FavoriteRepositoryInterface<List<Word>>> { FavoriteRepository(dataSource = get(named("favorite"))) }
	single<FavoriteInteraptorInterface<AppState>> { FavoriteInteraptor(favoriteRepository = get()) }
	viewModel(named(FAVORITE_VIEWMODEL)) { FavoriteViewModel(interaptor = get(), dispatcher = get())}
}
