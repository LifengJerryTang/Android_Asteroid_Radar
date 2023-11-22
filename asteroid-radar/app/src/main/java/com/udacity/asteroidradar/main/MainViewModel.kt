package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidsApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import org.json.JSONObject

import java.util.Calendar
import java.util.Locale

class MainViewModel : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()

    val properties: LiveData<List<Asteroid>>
        get() = _asteroids

    init {
        getAsteroids()
    }

    private fun getAsteroids() {

        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            val today = formatter.format(calendar.time)

            calendar.add(Calendar.DAY_OF_YEAR, 1)

            val tomorrow = formatter.format(calendar.time)

            val response = AsteroidsApi.retrofitService.getAsteroidResponse(today, tomorrow)
            val parsedResponse = parseAsteroidsJsonResult(JSONObject(response.string()))

            println(parsedResponse)

        }

    }
}