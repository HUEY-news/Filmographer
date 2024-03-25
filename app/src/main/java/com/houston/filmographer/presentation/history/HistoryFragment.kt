package com.houston.filmographer.presentation.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.houston.filmographer.databinding.FragmentHistoryBinding
import com.houston.filmographer.domain.search.model.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment: Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HistoryViewModel>()
    private var adapter: HistoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HistoryAdapter()
        binding.historyRecyclerView.adapter = adapter
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.fillData()
        viewModel.observeState().observe(viewLifecycleOwner) { state -> render(state) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        binding.historyRecyclerView.adapter = null
    }

    private fun render(state: HistoryState) {
        when (state) {
            is HistoryState.Loading -> {
                showLoading()
                Log.d("TEST", "isLoading")
            }
            is HistoryState.Content -> {
                showContent(state.data)
                Log.d("TEST", "isContent")
                Log.d("TEST", "${state.data}")
            }
            is HistoryState.Empty -> {
                showEmpty(state.message)
                Log.d("TEST", "isEmpty")
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        adapter?.setData(emptyList())
        binding.historyRecyclerView.isVisible = false
        binding.errorMessage.text = ""
        binding.errorMessage.isVisible = false
    }

    private fun showContent(data: List<Movie>) {
        binding.progressBar.isVisible = false
        Log.i("TEST", "ShowContent(data): $data")
        adapter?.setData(data)
        binding.historyRecyclerView.isVisible = true
        binding.errorMessage.text = ""
        binding.errorMessage.isVisible = false
    }

    private fun showEmpty(message: String) {
        binding.progressBar.isVisible = false
        adapter?.setData(emptyList())
        binding.historyRecyclerView.isVisible = false
        binding.errorMessage.text = message
        binding.errorMessage.isVisible = true
    }
}