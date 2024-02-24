package com.houston.filmographer.presentation.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.houston.filmographer.databinding.FragmentDetailsBinding
import com.houston.filmographer.presentation.search.SearchFragment

class DetailsFragment: Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TEST", "DETAILS FRAGMENT CREATED")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val poster = requireArguments().getString(POSTER_URL) ?: ""
        val movieId = requireArguments().getString(MOVIE_ID) ?: ""

        binding.viewPager.adapter = DetailsViewPagerAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle,
            posterUrl =  poster,
            movieId =  movieId
        )

        tabLayoutMediator = TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->
            when(position) {
                0 -> tab.text = "ПОСТЕР"
                1 -> tab.text = "О ФИЛЬМЕ"
            }
        }
        tabLayoutMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TEST", "DETAILS FRAGMENT DESTROYED")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabLayoutMediator.detach()
    }

    companion object {
        const val TAG = "DETAILS_FRAGMENT"
        private const val MOVIE_ID = "MOVIE_ID"
        private const val POSTER_URL = "POSTER_URL"

        fun newInstance(movieId: String, posterUrl: String): Fragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundleOf(MOVIE_ID to movieId, POSTER_URL to posterUrl)
            return fragment
        }
    }
}