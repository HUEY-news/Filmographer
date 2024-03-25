package com.houston.filmographer.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.houston.filmographer.R
import com.houston.filmographer.databinding.FragmentSearchBinding
import com.houston.filmographer.domain.search.model.Movie
import com.houston.filmographer.presentation.ToastState
import com.houston.filmographer.presentation.details.DetailsFragment
import com.houston.filmographer.presentation.root.RootActivity
import com.houston.filmographer.util.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private var adapter: SearchAdapter? = null
    private var watcher: TextWatcher? = null
    private lateinit var onClickDebounce: (Movie) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(
            inflater,
            container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickDebounce = debounce<Movie>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { movie ->
            findNavController().navigate(
                R.id.action_search_to_details,
                DetailsFragment.createArgs(
                    movieId = movie.id,
                    posterUrl = movie.image
                )
            )
        }

        adapter = SearchAdapter(object : SearchAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                (requireActivity() as RootActivity).animateBottomNavigationView()
                onClickDebounce(movie)
            }

            override fun onFavoriteClick(movie: Movie) {
                viewModel.switchFavorite(movie)
            }
        })

        viewModel.observeState().observe(viewLifecycleOwner) { state -> render(state) }
        viewModel.observeToast().observe(viewLifecycleOwner) { state ->
            if (state is ToastState.Show) {
                showToast(state.message)
                viewModel.switchToastState()
            }
        }

        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.adapter = adapter

        watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchDebounce(text = text?.toString() ?: "")
            }
        }
        watcher?.let { watcher -> binding.editText.addTextChangedListener(watcher) }

        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.sendRequest(text = binding.editText.text.toString())
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        binding.searchRecyclerView.adapter = null
        watcher?.let { binding.editText.removeTextChangedListener(it) }
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> showLoading()
            is SearchState.Content -> showContent(state.data)
            is SearchState.Error -> showError(state.message)
            is SearchState.Empty -> showEmpty(state.message)
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        adapter?.setData(emptyList())
        binding.searchRecyclerView.isVisible = false
        binding.textViewErrorMessage.text = ""
        binding.textViewErrorMessage.isVisible = false
    }

    private fun showContent(data: List<Movie>) {
        binding.progressBar.isVisible = false
        adapter?.setData(data)
        binding.searchRecyclerView.isVisible = true
        binding.textViewErrorMessage.text = ""
        binding.textViewErrorMessage.isVisible = false
    }

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        adapter?.setData(emptyList())
        binding.searchRecyclerView.isVisible = false
        binding.textViewErrorMessage.text = message
        binding.textViewErrorMessage.isVisible = true
    }

    private fun showEmpty(message: String) {
        showError(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}