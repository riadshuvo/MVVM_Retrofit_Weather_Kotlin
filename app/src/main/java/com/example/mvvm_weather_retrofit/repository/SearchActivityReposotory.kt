package com.example.mvvm_weather_retrofit.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_weather_retrofit.network.BASE_URL
import com.example.mvvm_weather_retrofit.network.RetrofitRepo
import com.example.mvvm_weather_retrofit.network.WeatherNetwork
import com.example.mvvm_weather_retrofit.network.model.Location
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivityReposotory(val application: Application) {
    val showProgress = MutableLiveData<Boolean>()
    val locationList = MutableLiveData<List<Location>>()

    fun changeState() {
        showProgress.value = !(showProgress.value != null && showProgress.value!!)
    }

    fun searchLocation(licationSearch: String) {
        //show progressbar before searching location
        showProgress.value = true

        //network call
        val weatherRetrofitService = RetrofitRepo.buildService(WeatherNetwork::class.java)

        val getLocation = weatherRetrofitService.getLocaton(licationSearch)

            getLocation.enqueue(object : Callback<List<Location>>{
            override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                //wipe progressbar after searching complete
                showProgress.value = false
                Toast.makeText(application,"error message: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<Location>>,
                response: Response<List<Location>>
            ) {
                Log.d("searchReposotory","Response: ${Gson().toJson(response.body())}")
                locationList.value = response.body()
                //wipe progressbar after searching complete
                showProgress.value = false
            }

        })
    }


}