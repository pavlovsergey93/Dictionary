package com.gmail.pavlovsv93.dictionary.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.databinding.FragmentFavoriteBinding
import com.gmail.pavlovsv93.app_entities.Word
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class FavoriteFragment : Fragment() {
	interface OnClickWord {
		fun onClickWord(word: com.gmail.pavlovsv93.app_entities.Word)
		fun onClickToFavorite(word: com.gmail.pavlovsv93.app_entities.Word, favoriteState: Boolean)
	}

	companion object {
		fun newInstance() = FavoriteFragment()
	}

	private var _binding: FragmentFavoriteBinding? = null
	private val binding get() = _binding!!
	private val viewModel: FavoriteViewModel by viewModel(named(com.gmail.pavlovsv93.utils.FAVORITE_VIEWMODEL))
	private val adapter: FavoriteAdapter = FavoriteAdapter(object : OnClickWord {
		override fun onClickWord(word: com.gmail.pavlovsv93.app_entities.Word) {
			Snackbar.make(binding.root, word.word, Snackbar.LENGTH_LONG).show()
		}

		override fun onClickToFavorite(word: com.gmail.pavlovsv93.app_entities.Word, favoriteState: Boolean) {
			viewModel.deleteIsFavorite(word.id.toInt())
		}
	})

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentFavoriteBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val recyclerView = binding.rvFavorite
		recyclerView.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		recyclerView.adapter = adapter
		viewModel.getDataViewModel().observe(viewLifecycleOwner, Observer { state ->
			rangeData(state)
		})
		viewModel.getFavoritesList()
	}

	private fun rangeData(state: AppState) {
		when (state) {
			is AppState.Error -> {
				Snackbar.make(binding.root, state.error, Snackbar.LENGTH_LONG).show()
			}
			is AppState.Loading -> {
				binding.pbFavoriteProgress.visibility = if (state.progress != null){
					View.GONE
				} else {
					View.VISIBLE
				}
			}
			is AppState.Success -> {
				adapter.setData(state.data)
			}
		}
	}
}