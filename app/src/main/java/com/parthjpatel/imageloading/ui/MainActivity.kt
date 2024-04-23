package com.parthjpatel.imageloading.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.parthjpatel.imageloading.adapter.ImageItemAdapter
import com.parthjpatel.imageloading.databinding.ActivityMainBinding
import com.parthjpatel.imageloading.network.NetworkResult


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var imageItemAdapter: ImageItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
        setupObservers()

    }

    private fun initialize() {
        imageItemAdapter = ImageItemAdapter()
        binding.rvImages.apply {
            adapter = imageItemAdapter
        }
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setupObservers() {
        mainViewModel.imageList.observe(this@MainActivity, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                    Log.e("Loading", "")
                    binding.circularLoader.visibility = View.VISIBLE
                }
                is NetworkResult.Success -> {
                    binding.circularLoader.visibility = View.GONE
                    if (it.data.isNullOrEmpty()) {
                        Log.e("Error", "List is empty")
                    } else {
                        imageItemAdapter.setupData(it.data)
                    }
                }
                is NetworkResult.Error -> {
                    binding.circularLoader.visibility = View.GONE
                    Log.e("Error", it.message.toString())
                }
            }
        })
    }


}