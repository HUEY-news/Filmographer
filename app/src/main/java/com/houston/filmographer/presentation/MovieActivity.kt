package com.houston.filmographer.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.houston.filmographer.data.MovieResponse
import com.houston.filmographer.data.TVapi
import com.houston.filmographer.databinding.ActivityMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieActivity : AppCompatActivity() {

    private var _binding: ActivityMovieBinding? = null
    private val binding get() = _binding!!

    private val baseUrl = "https://tv-api.com"
    private val key = "k_zcuw1ytf"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(TVapi::class.java)
    private val adapter = MovieAdapter { movie ->
        if (clickDebounce()) {
            val intent = Intent(this, PosterActivity::class.java)
            intent.putExtra("POSTER", movie.image)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        binding.editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchDebounce()
            }
        })

        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) searchDebounce()
            false
        }
    }

    private fun searchMovie(key: String, query: String) {
        if (query.isNotEmpty()) {
            adapter.setContent(emptyList())
            service.searchMovie(key, query)
                .enqueue(object : Callback<MovieResponse> {

                    override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                    ) {
                        if (response.code() == 200) {
                            if (response.body()?.results?.isNotEmpty() == true) {
                                showMessage("", "")
                                adapter.setContent(response.body()?.results!!)
                            } else showMessage("Ничего не найдено", "")
                        } else showMessage("Что-то не так", response.code().toString())
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        showMessage("Что-то не так", t.message.toString())
                    }
                })
        }
    }

    private fun showMessage(mainMessage: String, additionalMessage: String) {
        if (mainMessage.isNotEmpty()) {
            binding.textView.isVisible = true
            adapter.setContent(emptyList())
            binding.textView.text = mainMessage
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(
                    applicationContext,
                    additionalMessage,
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        } else binding.textView.isVisible = false
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

    private val searchRunnable = Runnable {
        searchMovie(key, binding.editText.text.toString())
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}