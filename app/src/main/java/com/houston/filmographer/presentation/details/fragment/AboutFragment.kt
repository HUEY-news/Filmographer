package com.houston.filmographer.presentation.details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.houston.filmographer.databinding.FragmentAboutBinding
import com.houston.filmographer.domain.model.MovieDetails
import com.houston.filmographer.presentation.details.AboutState
import com.houston.filmographer.presentation.details.view_model.AboutViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<AboutViewModel>() {
        parametersOf(requireArguments().getString(MOVIE_ID))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is AboutState.Content -> { showDetails(state.movieDetails) }
                is AboutState.Error -> { showErrorMessage(state.message) }
            }
        }
    }

    private fun showDetails(movieDetails: MovieDetails) {
        binding.apply {
            constraintLayoutDetails.visibility = View.VISIBLE
            textViewErrorMessage.visibility = View.GONE
            textViewTitle.text = movieDetails.title
            textViewRatingValue.text = movieDetails.imDbRating
            textViewYearValue.text = movieDetails.year
            textViewCountryValue.text = movieDetails.countries
            textViewGenreValue.text = movieDetails.genres
            textViewDirectorValue.text = movieDetails.directors
            textViewWriterValue.text = movieDetails.writers
            textViewCastValue.text = movieDetails.stars
            textViewPlot.text = movieDetails.plot
        }
    }

    private fun showErrorMessage(message: String) {
        binding.apply {
            constraintLayoutDetails.visibility = View.GONE
            textViewErrorMessage.visibility = View.VISIBLE
            textViewErrorMessage.text = message
        }
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movieId: String) =
            AboutFragment().apply {
                arguments = bundleOf(MOVIE_ID to movieId)
            }
    }
}