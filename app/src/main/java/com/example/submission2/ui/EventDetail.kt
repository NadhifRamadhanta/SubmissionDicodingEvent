package com.example.submission2.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.submission2.R
import com.example.submission2.data.response.ListEventsItem
import com.example.submission2.databinding.ActivityEventDetailBinding
import com.example.submission2.databinding.FragmentUpcomingEventBinding
import com.example.submission2.other.GlideApp

class EventDetail : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idEvent = intent.getStringExtra(EXTRA_EVENT)

        mainViewModel.getDetailEvent(idEvent!!)

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        mainViewModel.detailEvent.observe(this){
            dataEvent ->
            GlideApp.with(binding.root.context).load(dataEvent?.imageLogo).into(binding.eventPoster)
            binding.name.text = dataEvent?.name
            binding.ownerName.text = dataEvent?.ownerName
            binding.quota.text = "Quota: " + dataEvent?.quota.toString()
            binding.beginTime.text = dataEvent?.beginTime
            binding.description.text = HtmlCompat.fromHtml(
                dataEvent?.description.toString(),HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            binding.buttonLink.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(dataEvent?.link)))
            }
        }

    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.containerDetail.visibility = View.GONE
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.containerDetail.visibility = View.VISIBLE
            binding.progressBarDetail.visibility = View.GONE
        }
    }



    companion object{
        const val EXTRA_EVENT = "extra_event"
    }
}