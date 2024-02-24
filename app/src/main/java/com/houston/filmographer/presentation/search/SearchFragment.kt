package com.houston.filmographer.presentation.search

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.houston.filmographer.databinding.FragmentSearchBinding
import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.presentation.details.DetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SearchViewModel>()
    private var watcher: TextWatcher? = null

    private val adapter = SearchAdapter(object: SearchAdapter.MovieClickListener {

        override fun onMovieClick(movie: Movie) {
            if (clickDebounce()) {
                val intent = Intent(requireContext(), DetailsActivity::class.java)
                intent.putExtra("POSTER", movie.image)
                intent.putExtra("ID", movie.id)
                startActivity(intent)
            }
        }

        override fun onFavoriteClick(movie: Movie) {
            viewModel.switchFavorite(movie)
        }
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState().observe(viewLifecycleOwner) { state -> render(state) }
        viewModel.observeToast().observe(viewLifecycleOwner) { state ->
            if (state is ToastState.Show) {
                showToast(state.message)
                viewModel.switchToastState()
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(text = text?.toString() ?: "")
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
        Log.e("TEST", "SEARCH ACTIVITY DESTROYED")
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
        adapter.setData(emptyList())
        binding.recyclerView.isVisible = false
        binding.textViewErrorMessage.text = ""
        binding.textViewErrorMessage.isVisible = false
    }

    private fun showContent(data: List<Movie>) {
        binding.progressBar.isVisible = false
        adapter.setData(data)
        binding.recyclerView.isVisible = true
        binding.textViewErrorMessage.text = ""
        binding.textViewErrorMessage.isVisible = false
    }

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        adapter.setData(emptyList())
        binding.recyclerView.isVisible = false
        binding.textViewErrorMessage.text = message
        binding.textViewErrorMessage.isVisible = true
    }

    private fun showEmpty(message: String) {
        showError(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}