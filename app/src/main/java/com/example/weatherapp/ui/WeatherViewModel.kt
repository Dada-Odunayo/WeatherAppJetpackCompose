package com.example.weatherapp.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.utils.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val apiService: ApiService
):ViewModel() {
    private val _data = MutableStateFlow<WeatherResponse?>(null)
    val data : StateFlow<WeatherResponse?> get() = _data


    private val _text = mutableStateOf("")
    val text: State<String> get() = _text

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() =  _isLoading

    fun onTextChange(newValue: String) {
        _text.value = newValue
    }

    fun getWeather(){
        viewModelScope.launch {
        try {
            _isLoading.value = true
            val response = apiService.getWeatherData(location = _text.toString() )
            _isLoading.value = false
            if(response.isSuccessful){

                _data.value = response.body()
                Log.i("Get Weather",response.body().toString())
            }
            else{

                Log.d("Api call not successful",response.errorBody().toString())
            }
        }
        catch (e:Exception){
            _isLoading.value = false
            Log.d("Fetch Weather Details",e.message.toString())
        }

        }
    }
}