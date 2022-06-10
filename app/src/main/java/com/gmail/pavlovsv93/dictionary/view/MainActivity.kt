package com.gmail.pavlovsv93.dictionary.view

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.pavlovsv93.dictionary.R
import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.databinding.ActivityMainBinding
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import com.gmail.pavlovsv93.dictionary.utils.isOnline
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val SAVE_STATE_RV = "SAVE_STATE_RV"

class MainActivity : AppCompatActivity(), ViewInterface {
	interface OnClickWord {
		fun onClickWord(word: Word)
	}

	private var searchWord: String? = null
	private lateinit var binding: ActivityMainBinding
	private val viewModel: MainViewModel by viewModel()
	private val adapter = MainRvAdapter(object : OnClickWord {
		override fun onClickWord(word: Word) {
			showError(word.word, false)
		}
	})

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		val recyclerView = binding.rvMainDictionary
		recyclerView.layoutManager =
			LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
		recyclerView.adapter = adapter
		binding.tilSearchWord.setEndIconOnClickListener() {
			hideKeyBoard()
			searchWord = binding.etSearchWord.text?.toString()
			if (searchWord != null && searchWord != "") {
				viewModel.getDataViewModel(searchWord!!, binding.root.isOnline(this@MainActivity))
					.observe(this, Observer { state ->
						rangeData(state)
					})
			}
		}
	}

	override fun rangeData(state: AppState) {
		when (state) {
			is AppState.Error -> {
				showError(state.error, true, isOnline = true)
			}
			is AppState.Loading -> {
				binding.pbSearch.isVisible = state.progress == null
			}
			is AppState.Success -> {
				val receivedData = state.data
				if (!receivedData.isNullOrEmpty()) {
					showData(receivedData)
				} else {
					showError(
						resources.getString(R.string.empty_server_response_on_success),
						true,
						isOnline = false
					)
				}
			}
		}
	}

	private fun showData(data: List<Word>) {
		adapter.setDataInRv(data)
	}

	private fun showError(message: String, isAction: Boolean, isOnline: Boolean = true) {
		val sb = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
		if (isAction) {
			if (isOnline) {
				sb.setAction("Reload Online") {
					if (!searchWord.isNullOrEmpty()) {
						viewModel.getDataViewModel(searchWord!!, true)
					} else {
						showError(resources.getString(R.string.error_empty), false)
					}
				}
			} else {
				sb.setAction("Reload Local") {
					if (!searchWord.isNullOrEmpty()) {
						viewModel.getDataViewModel(searchWord!!, false)
					} else {
						showError(resources.getString(R.string.error_empty), false)
					}
				}
			}
		}
		sb.show()
	}

	private fun hideKeyBoard() {
		val view = this.currentFocus
		if (view != null) {
			val imm: InputMethodManager =
				getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
			imm.hideSoftInputFromWindow(view.windowToken, 0);
		}
	}
}