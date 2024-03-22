package com.houston.filmographer.presentation.name

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.houston.filmographer.databinding.FragmentNameBinding
import com.houston.filmographer.domain.model.Person
import com.houston.filmographer.presentation.ToastState
import org.koin.androidx.viewmodel.ext.android.viewModel

class NameFragment : Fragment() {

    private var _binding: FragmentNameBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<NameViewModel>()
    private var watcher: TextWatcher? = null
    private val adapter = PersonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TEST", "NAME FRAGMENT CREATED")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNameBinding.inflate(inflater, container, false)
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
        watcher?.let { binding.editText.removeTextChangedListener(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("TEST", "NAME FRAGMENT DESTROYED")
    }

    private fun render(state: NameState) {
        when (state) {
            is NameState.Loading -> showLoading()
            is NameState.Content -> showContent(state.data)
            is NameState.Error -> showError(state.message)
            is NameState.Empty -> showEmpty(state.message)
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        adapter.setData(emptyList())
        binding.recyclerView.isVisible = false
        binding.textViewErrorMessage.text = ""
        binding.textViewErrorMessage.isVisible = false
    }

    private fun showContent(data: List<Person>) {
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
}