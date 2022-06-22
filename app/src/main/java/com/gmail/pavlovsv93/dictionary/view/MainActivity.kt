package com.gmail.pavlovsv93.dictionary.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.pavlovsv93.dictionary.R
import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.databinding.ActivityMainBinding
import com.gmail.pavlovsv93.dictionary.utils.MAIN_VIEWMODEL
import com.gmail.pavlovsv93.dictionary.utils.RELOAD_LOCAL
import com.gmail.pavlovsv93.dictionary.utils.RELOAD_ONLINE
import com.gmail.pavlovsv93.dictionary.utils.isOnline
import com.gmail.pavlovsv93.dictionary.view.entityes.Word
import com.gmail.pavlovsv93.dictionary.view.favorite.FavoriteFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

const val SAVE_STATE_RV = "SAVE_STATE_RV"

class MainActivity : AppCompatActivity(), ViewInterface {
	interface OnClickWord {
		fun onClickWord(word: Word)
		fun onClickToFavorite(word: Word, favoriteState: Boolean)
	}

	private var flag: Boolean = false
	private var searchWord: String? = null
	private lateinit var binding: ActivityMainBinding
	private val viewModel: MainViewModel by viewModel(named(MAIN_VIEWMODEL))
	private val adapter = MainRvAdapter(object : OnClickWord {
		override fun onClickWord(word: Word) {
			//Показать слово по нажатию
			showError(word.word, false)
		}

		override fun onClickToFavorite(word: Word, favoriteState: Boolean) {
			word.isFavorite = favoriteState
			viewModel.setFavorite(word)
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
			if (!searchWord.isNullOrEmpty()) {
				viewModel.getDataViewModel(searchWord!!, binding.root.isOnline(this@MainActivity))
					.observe(this, Observer { state ->
						rangeData(state)
					})
			}
		}
		binding.fabFavorite.setOnClickListener {
			flag = !flag
			if (flag) {
				binding.llContainer.visibility = View.GONE
				binding.fcvContainer.visibility = View.VISIBLE
				binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
				supportFragmentManager.beginTransaction()
					.replace(R.id.fcvContainer, FavoriteFragment.newInstance())
					.commit()
			} else {
				binding.llContainer.visibility = View.VISIBLE
				binding.fcvContainer.visibility = View.GONE
				binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
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
				sb.setAction(RELOAD_ONLINE) {
					if (!searchWord.isNullOrEmpty()) {
						viewModel.getDataViewModel(searchWord!!, true)
					} else {
						showError(resources.getString(R.string.error_empty), false)
					}
				}
			} else {
				sb.setAction(RELOAD_LOCAL) {
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