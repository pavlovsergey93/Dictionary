package com.gmail.pavlovsv93.dictionary.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.pavlovsv93.dictionary.data.AppState
import com.gmail.pavlovsv93.dictionary.databinding.ActivityMainBinding
import com.gmail.pavlovsv93.dictionary.presenter.InteraptorInterface
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}
}