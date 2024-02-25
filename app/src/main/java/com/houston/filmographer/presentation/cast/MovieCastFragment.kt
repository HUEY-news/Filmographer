package com.houston.filmographer.presentation.cast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.houston.filmographer.databinding.FragmentMovieCastBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieCastFragment: Fragment() {

    private var _binding: FragmentMovieCastBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<MovieCastViewModel> {
        parametersOf(requireArguments().getString(MOVIE_ID))
    }

    // TODO: - Реализовать библиотеку AdapterDelegates...
    // TODO: - заменить private val adapter = MovieCastAdapter()
    // TODO: - на private val adapter = ListDelegationAdapter(movieCastHeaderDelegate(), movieCastPersonDelegate())
    private val adapter = MovieCastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TEST", "MOVIE CAST FRAGMENT CREATED")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieCastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.observeState().observe(viewLifecycleOwner) { state -> render(state) }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TEST", "MOVIE CAST FRAGMENT DESTROYED")
    }

    private fun render(state: MovieCastState) {
        when (state) {
            is MovieCastState.Loading -> showLoading()
            is MovieCastState.Content -> showContent(state)
            is MovieCastState.Error -> showError(state.message)
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        adapter.setData(emptyList())
        binding.linearLayout.isVisible = false
        binding.textViewErrorMessage.text = ""
        binding.textViewErrorMessage.isVisible = false
    }

    private fun showContent(state: MovieCastState.Content) {
        binding.progressBar.isVisible = false
        binding.textViewMovieTitle.text = state.title
        adapter.setData(items = state.items)
        binding.linearLayout.isVisible = true
        binding.textViewErrorMessage.text = ""
        binding.textViewErrorMessage.isVisible = false
    }

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        adapter.setData(emptyList())
        binding.linearLayout.isVisible = false
        binding.textViewErrorMessage.text = message
        binding.textViewErrorMessage.isVisible = true
    }

    companion object {
        const val TAG = "MOVIE_CAST_FRAGMENT"
        private const val MOVIE_ID = "MOVIE_ID"

        fun newInstance(movieId: String): Fragment {
            val fragment = MovieCastFragment()
            fragment.arguments = bundleOf(MOVIE_ID to movieId)
            return fragment
        }
    }
}