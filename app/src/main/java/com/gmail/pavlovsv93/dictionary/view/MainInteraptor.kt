package com.gmail.pavlovsv93.dictionary.view

import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.data.LocalDataSource
import com.gmail.pavlovsv93.dictionary.data.repository.RepositoryInterface
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import io.reactivex.rxjava3.core.Observable

class MainInteraptor(
	private val remoteRepository: RepositoryInterface<List<Word>>,
	private val localRepository: RepositoryInterface<List<Word>>
) : InteraptorInterface<AppState> {
	override fun getDataInteraptor(
		word: String, fromRemoteSource: Boolean
	): Observable<AppState> {
		return if (fromRemoteSource) {
			remoteRepository.getDataRepository(word)
				.map { AppState.Success(it) }
		} else {
			localRepository.getDataRepository(word)
				.map { AppState.Success(it) }
		}
	}
}