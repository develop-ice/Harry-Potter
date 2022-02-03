package com.gmail.orlandroyd.myapplication.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gmail.orlandroyd.myapplication.R
import com.gmail.orlandroyd.myapplication.databinding.ActivityMainBinding
import com.gmail.orlandroyd.myapplication.networking.DataState
import com.gmail.orlandroyd.myapplication.util.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // BINDING
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    // VIEW MODEL
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init binding
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.setHasFixedSize(true)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characters.collect {
                    when (it.status) {

                        DataState.Status.SUCCESS -> {

                            displayProgressBar(false)
                            val characterAdapter = CharacterAdapter()
                            characterAdapter.submitList(it.data)
                            binding.recycler.adapter = characterAdapter

                        }

                        DataState.Status.ERROR -> {
                            displayProgressBar(false)
                            Utility.showSnack(
                                binding.root,
                                "${it.message}",
                                R.color.red_200,
                                Snackbar.LENGTH_LONG
                            )
                        }

                        DataState.Status.LOADING -> {
                            displayProgressBar(true)
                        }

                        DataState.Status.FAILURE -> {
                            displayProgressBar(false)
                            Utility.showSnack(
                                binding.root,
                                "${it.message}",
                                R.color.red_200,
                                Snackbar.LENGTH_LONG
                            )

                            val msg = it.data ?: emptyList()

                            toast(msg.toString())
                        }
                    }
                }.exhaustive
            }
        }

    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        if (isDisplayed) {
            binding.progress.setVisible()
            binding.recycler.setGone()
        } else {
            binding.progress.setGone()
            binding.recycler.setVisible()
        }
    }

    // OVERRIDE BINDING
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}