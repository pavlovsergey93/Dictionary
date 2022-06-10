package com.gmail.pavlovsv93.dictionary.view

import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.data.LocalDataSource
import com.gmail.pavlovsv93.dictionary.data.repository.RepositoryInterface
import com.gmail.pavlovsv93.dictionary.data.retrofit.SearchDTOItem
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class MainInteraptor(
	private val remoteRepository: RepositoryInterface<List<Word>>,
	private val localRepository: RepositoryInterface<List<Word>>
) : InteraptorInterface<AppState> {
	override suspend fun getDataInteraptor(
		word: String, fromRemoteSource: Boolean
	): Response<List<SearchDTOItem>> {
		return if (fromRemoteSource) {
			remoteRepository.getDataRepository(word)
		} else {
			localRepository.getDataRepository(word)
		}
	}
}