package com.gmail.pavlovsv93.dictionary.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.gmail.pavlovsv93.dictionary.R
import com.gmail.pavlovsv93.dictionary.databinding.ActivityMainRvItemBinding
import com.gmail.pavlovsv93.dictionary.view.entityes.Word

class MainRvAdapter(val onClick: MainActivity.OnClickWord) :
	RecyclerView.Adapter<MainRvAdapter.MainRvViewHolder>() {

	inner class MainRvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(word: Word) {
			ActivityMainRvItemBinding.bind(itemView).apply {
				tvHeaderItem.text = word.word
				tvDescriptionItem.text = word.meanings.translation?.text
				word.meanings.imageUrl?.let {
					Glide.with(itemView.context)
						.load(word.meanings.imageUrl)
						.centerCrop()
						.transform(MultiTransformation(CircleCrop(), FitCenter()))
						.placeholder(R.drawable.ic_baseline_image_24)
						.into(ivImageWord)
				}
				if (word.isFavorite){
					ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
				} else {
					ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
				}
				llContainerItem.setOnClickListener {
					onClick.onClickWord(word)
				}
				ivFavorite.setOnClickListener {
					if(word.isFavorite){
						ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
					} else {
						ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
					}
					onClick.onClickToFavorite(word, !word.isFavorite)
					notifyItemChanged(layoutPosition)
				}
			}
		}
	}

	private val listData: MutableList<Word> = mutableListOf()

	@SuppressLint("NotifyDataSetChanged")
	fun setDataInRv(data: List<Word>) {
		listData.clear()
		listData.addAll(data)
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRvViewHolder {
		return MainRvViewHolder(
			LayoutInflater.from(parent.context)
				.inflate(R.layout.activity_main_rv_item, parent, false)
					as View
		)
	}

	override fun onBindViewHolder(holder: MainRvViewHolder, position: Int) {
		holder.bind(listData[position])
	}

	override fun getItemCount(): Int = listData.size
}