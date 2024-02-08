package com.houston.filmographer.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.houston.filmographer.data.ApplicationProgrammingInterface
import com.houston.filmographer.data.AuthorizationRequest
import com.houston.filmographer.data.AuthorizationResponse
import com.houston.filmographer.data.LocationResponse
import com.houston.filmographer.data.WeatherResponse
import com.houston.filmographer.databinding.ActivityMainBinding
import com.houston.filmographer.domain.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val baseUrl = "https://pfa.foreca.com"
    private val userName = "huey-news"
    private val password = "DvKWYdLgTakb"
    private var token = ""

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(ApplicationProgrammingInterface::class.java)

    private val adapter = LocationAdapter { showWeather(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        binding.button.setOnClickListener {
            if (binding.editText.text.isNotEmpty()) {
                if (token.isEmpty()) authorization()
                else search()
            }
        }
    }

    private fun showMessage(mainMessage: String, additionalMessage: String) {

        if (mainMessage.isNotEmpty()) {
            binding.textView.isVisible = true
            adapter.setContent(emptyList())
            binding.textView.text = mainMessage

            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }

        } else binding.textView.isVisible = false
    }

    private fun authorization() {
        service.authorize(AuthorizationRequest(userName, password))
            .enqueue(object: Callback<AuthorizationResponse> {

                override fun onResponse(call: Call<AuthorizationResponse>, response: Response<AuthorizationResponse>) {
                    if (response.code() == 200) {
                        token = response.body()?.token.toString()
                        search()
                    } else {
                        showMessage(
                            "Что-то пошло не так",
                            response.code().toString())
                    }
                }

                override fun onFailure(call: Call<AuthorizationResponse>, t: Throwable) {
                    showMessage(
                        "Не удалось авторизоваться",
                        t.message.toString()
                    )
                }
            })
    }

    private fun search() {
        service.getLocations("Bearer $token", binding.editText.text.toString())
            .enqueue(object: Callback<LocationResponse> {
                override fun onResponse(
                    call: Call<LocationResponse>,
                    response: Response<LocationResponse>
                ) {
                    when (response.code()) {

                        200 -> {
                            if (response.body()?.locations?.isNotEmpty() == true) {
                                adapter.setContent(response.body()?.locations!!)
                                showMessage("", "")
                            } else showMessage("Ничего не найдено", "")
                        }

                        401 -> authorization()

                        else -> showMessage("Что-то пошло не так", response.code().toString())
                    }
                }

                override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                    showMessage(
                        "Запрос отклонён",
                        t.message.toString()
                    )
                }
            })
    }

    private fun showWeather(location: Location) {
        service.getWeather("Bearer $token", location.id)
            .enqueue(object: Callback<WeatherResponse> {

                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.body()?.current != null) {
                        val message = "${location.name} t: ${response.body()?.current?.temperature}" +
                                      "\n(Ощущается как ${response.body()?.current?.feelsLikeTemp})"
                        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })
    }
}