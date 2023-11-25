package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _asteroids = MutableLiveData<List<Asteroid>>()

    val properties: LiveData<List<Asteroid>>
        get() = _asteroids

    init {
        getAsteroids()
    }

    private fun getAsteroids() {

        viewModelScope.launch {

            try {
                val calendar = Calendar.getInstance()
                val formatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
                val today = formatter.format(calendar.time)

                calendar.add(Calendar.DAY_OF_YEAR, 1)

                val tomorrow = formatter.format(calendar.time)
                val response = AsteroidsApi.retrofitService.getAsteroidResponse(today, tomorrow)

                _asteroids.value = parseAsteroidsJsonResult(JSONObject(response.string()))


            } catch (e: Exception) {
                val jsonString = readJsonFile("asteroids.json")
                val jsonObject = JSONObject(jsonString)

                println("Trying to read json")

                _asteroids.value = parseAsteroidsJsonResult(jsonObject);

                for (asteroid in parseAsteroidsJsonResult(jsonObject)) {
                    println("an asteroid:")
                    println(asteroid.codename)
                }


            }

        }

    }

    private fun readJsonFile(fileName: String): String {
        return getApplication<Application>().assets.open(fileName).bufferedReader().use { it.readText() }
    }
}