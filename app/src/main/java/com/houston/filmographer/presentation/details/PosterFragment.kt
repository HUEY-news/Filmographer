package com.houston.filmographer.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.houston.filmographer.databinding.FragmentPosterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PosterFragment : Fragment() {

    private var _binding: FragmentPosterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<PosterViewModel>() {
        parametersOf(requireArguments().getString(POSTER_URL))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeUrl().observe(viewLifecycleOwner) { url -> showPoster(url) }
    }

    private fun showPoster(url: String) {
        context?.let {
            Glide.with(it)
                .load(url)
                .into(binding.imageViewCover)
        }
    }

    companion object {
        private const val POSTER_URL = "poster_url"

        fun newInstance(posterUrl: String) =
            PosterFragment().apply {
                arguments = bundleOf(POSTER_URL to posterUrl)
            }
    }
}