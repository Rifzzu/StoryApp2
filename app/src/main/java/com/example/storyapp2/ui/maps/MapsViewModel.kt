package com.example.storyapp2.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyapp2.data.api.ApiConfig
import com.example.storyapp2.data.response.ListStoryItem
import com.example.storyapp2.data.response.StoriesResponse
import com.example.storyapp2.data.local.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val pref: UserPreference) : ViewModel() {

    private val _story = MutableLiveData<List<ListStoryItem>>()
    val story: LiveData<List<ListStoryItem>> = _story

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val client = ApiConfig.getApiService()

    fun stateData(): LiveData<String> {
        return pref.getKey().asLiveData()
    }

    fun getAllStoriesWithLocation(token: String){
        _loading.value = true
        client.getAllStoriesWithLocation("Bearer $token", 1).enqueue(object : Callback<StoriesResponse>{
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                _loading.value = false
                _message.value = response.body()?.message
                if (response.isSuccessful){
                    _story.value = response.body()?.listStory
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                _loading.value = false
                _message.value = t.message
            }

        })
    }
}