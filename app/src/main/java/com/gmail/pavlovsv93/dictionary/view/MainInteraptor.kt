package com.gmail.pavlovsv93.dictionary.view

import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.data.repository.RepositoryInterface
import com.gmail.pavlovsv93.dictionary.data.retrofit.SearchDTOItem
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import retrofit2.Response

class MainInteraptor(
	private val remoteRepository: RepositoryInterface<List<Word>>,
	private val localRepository: RepositoryInterface<List<Word>>
) : InteraptorInterface<AppState> {
	override suspend fun getDataInteraptor(
		word: String, fromRemoteSource: Boolean
	): List<Word> {
		return if (fromRemoteSource) {
			remoteRepository.getDataRepository(word)
		} else {
			localRepository.getDataRepository(word)
		}
	}

	override suspend fun setDataLocal(words: List<Word>) {
		localRepository.setDataLocal(words)
	}
}