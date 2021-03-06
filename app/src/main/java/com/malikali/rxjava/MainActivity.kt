package com.malikali.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import com.malikali.rxjava.databinding.ActivityMainBinding
import com.malikali.rxjava.viewmodels.AnimalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel:AnimalViewModel by viewModels()
    private var progress:Boolean = true
    private lateinit var binding: ActivityMainBinding
    private var text = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            showProgress()
        }

        initObservers()

    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE

        viewModel.getARandomAnimal()
        val handler = Handler(Looper.getMainLooper())
        Thread {

               handler.postDelayed( {

                   if (!progress) {
                       binding.progressBar.visibility = View.GONE
                       binding.textView.text = text
                   }
               },1000)



        }.start()



    }

    private fun initObservers() {
        viewModel.animal.observe(this) {
            text = it.name
        }

        viewModel.isLoading.observe(this) {
            progress = it
        }
    }


}