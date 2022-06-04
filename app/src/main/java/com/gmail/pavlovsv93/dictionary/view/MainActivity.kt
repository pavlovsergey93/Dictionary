package com.gmail.pavlovsv93.dictionary.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.gmail.pavlovsv93.dictionary.R
import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.databinding.ActivityMainBinding
import com.gmail.pavlovsv93.dictionary.presenter.PresenterInterface
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), ViewInterface {
	private val searchWord: String? = null
	private lateinit var binding: ActivityMainBinding
	private val presenter: PresenterInterface by inject()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}

	override fun rangeData(state: AppState) {
		when (state) {
			is AppState.Error -> {
				showError(state.error.message.toString(), true)
			}
			is AppState.Loading -> {
				if (state.progress != null) {
					binding.pbSearch.visibility = View.GONE
					with(binding.pbLoading) {
						isVisible = true
						progress = state.progress
					}
				} else {
					binding.pbSearch.isVisible = true
					binding.pbLoading.visibility = View.GONE

				}
			}
			is AppState.Success -> {
				val receivedData = state.data
				if (!receivedData.isNullOrEmpty()) {
					showData(receivedData)
				} else {
					showError(resources.getString(R.string.empty_server_response_on_success), true)
				}
			}
		}
	}

	private fun showData(data: List<Word>) {
		TODO("Not yet implemented")
	}

	private fun showError(message: String, isAction: Boolean) {
		val sb = Snackbar.make(binding.root,message,Snackbar.LENGTH_INDEFINITE)
		if (isAction) {
			sb.setAction("Reload") {
				if (!searchWord.isNullOrEmpty()) {
					presenter.getDataPresenter(searchWord, true)
				} else {
					showError(resources.getString(R.string.error_empty), false)
				}
			}
		}
		sb.show()
	}
}