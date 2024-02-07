package com.houston.filmographer.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.houston.filmographer.data.Api
import com.houston.filmographer.data.TranslationRequest
import com.houston.filmographer.data.TranslationResponse
import com.houston.filmographer.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val baseUrl = "https://api.funtranslations.com"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Api::class.java)

        fun yodaTranslate(text: String) {
            service.translateToYoda(TranslationRequest(text))
                .enqueue(object: Callback<TranslationResponse> {
                    override fun onResponse(
                        call: Call<TranslationResponse>,
                        response: Response<TranslationResponse>
                    ) {
                        Log.i("TEST", "Status code: ${response.code()}")
                        Log.d("TEST", "Yoda says: ${response.body()?.contents?.translated}")
                        binding.textView.text = response.body()?.contents?.translated
                    }

                    override fun onFailure(call: Call<TranslationResponse>, t: Throwable) {
                        Log.e("TEST", "Error code: $t")
                    }
                })
        }

        fun morseTranslate(text: String) {
            service.translateToMorse(TranslationRequest(text))
                .enqueue(object: Callback<TranslationResponse> {
                    override fun onResponse(
                        call: Call<TranslationResponse>,
                        response: Response<TranslationResponse>
                    ) {
                        Log.i("TEST", "Status code: ${response.code()}")
                        Log.d("TEST", "${response.body()?.contents?.text} in Morse code: ${response.body()?.contents?.translated}")
                        binding.textView.text = response.body()?.contents?.translated
                    }

                    override fun onFailure(call: Call<TranslationResponse>, t: Throwable) {
                        Log.e("TEST", "Error code: $t")
                    }
                })
        }

        binding.buttonYoda.setOnClickListener {
            val input = binding.editText.text
            yodaTranslate(input.toString())
        }

        binding.buttonMorse.setOnClickListener {
            val input = binding.editText.text
            morseTranslate(input.toString())
        }
    }
}