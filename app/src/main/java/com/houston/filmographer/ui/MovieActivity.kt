package com.houston.filmographer.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.houston.filmographer.data.creator.Creator
import com.houston.filmographer.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {

    private var _binding: ActivityMovieBinding? = null
    val binding get() = _binding!!

//    private val interactor = Creator.provideMovieInteractor()
//    private val key = "k_zcuw1ytf"

    private val adapter = MovieAdapter { movie ->
        if (clickDebounce()) {
            val intent = Intent(this, PosterActivity::class.java)
            intent.putExtra("POSTER", movie.image)
            startActivity(intent)
        }
    }

    private val controller = Creator.provideMovieController(this, adapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller.onCreate()

//        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        binding.recyclerView.adapter = adapter

//        binding.editText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun afterTextChanged(s: Editable?) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                searchDebounce()
//            }
//        })

//        binding.editText.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                handler.removeCallbacks(searchRunnable)
//                handler.post(searchRunnable)
//            }
//            false
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
//        handler.removeCallbacks(searchRunnable)
    }

//    private fun searchMovie(key: String, query: String) {
//        Log.v("TEST", "Зпрос отправлен!")
//        if (query.isNotEmpty()) {
//            binding.progressBar.isVisible = true
//            adapter.setContent(emptyList())
//            showMessage("", "")
//            interactor.searchMovie(key, query, object : MovieInteractor.MovieConsumer {
//                override fun consume(movies: List<Movie>) {
//                    handler.post {
//                        binding.progressBar.isVisible = false
//                        adapter.setContent(movies)
//                        if (movies.isEmpty()) showMessage("Ничего не найдено", "")
//                        else showMessage("", "")
//                    }
//                }
//            })
//        } else showMessage("Ничего не найдено", "Поле ввода пустое")
//    }

//    private fun showMessage(mainMessage: String, additionalMessage: String) {
//        if (mainMessage.isNotEmpty()) {
//            binding.textView.isVisible = true
//            adapter.setContent(emptyList())
//            binding.textView.text = mainMessage
//            if (additionalMessage.isNotEmpty()) {
//                Toast.makeText(
//                    applicationContext,
//                    additionalMessage,
//                    Toast.LENGTH_LONG
//                )
//                    .show()
//            }
//        } else binding.textView.isVisible = false
//    }

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

//    private val searchRunnable = Runnable {
//        searchMovie(key, binding.editText.text.toString())
//    }

//    private fun searchDebounce() {
//        handler.removeCallbacks(searchRunnable)
//        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
//    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
//        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}