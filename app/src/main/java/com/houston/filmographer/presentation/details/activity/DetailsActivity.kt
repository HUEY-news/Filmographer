package com.houston.filmographer.presentation.details.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.houston.filmographer.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val poster = intent.getStringExtra("POSTER") ?: ""
        val movieId = intent.getStringExtra("ID") ?: ""

        binding.viewPager.adapter = DetailsViewPagerAdapter(supportFragmentManager,
            lifecycle, poster, movieId)

        mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "ПОСТЕР"
                1 -> tab.text = "О ФИЛЬМЕ"
            }
        }
        mediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediator.detach()
    }

}