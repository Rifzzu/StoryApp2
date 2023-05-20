package com.example.storyapp2.ui.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyapp2.data.api.ApiConfig
import com.example.storyapp2.data.response.GeneralResponse
import com.example.storyapp2.data.local.UserPreference
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryViewModel(private val pref: UserPreference) : ViewModel() {

    private val client = ApiConfig.getApiService()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun uploadStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ){
        _loading.value = true
        client.uploadStory(token = "Bearer $token", file, description).enqueue(object : Callback<GeneralResponse>{
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                _loading.value = false
                if (response.isSuccessful){
                    _message.value = response.body()?.message
                }else{
                    _message.value = response.message().toString()
                }
            }
            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                _loading.value = false
                _message.value = t.message
            }
        })
    }

    fun stateData(): LiveData<String> {
        return pref.getKey().asLiveData()
    }
}