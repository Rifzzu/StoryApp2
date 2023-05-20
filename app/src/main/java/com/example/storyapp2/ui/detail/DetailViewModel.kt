package com.example.storyapp2.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp2.data.api.ApiConfig
import com.example.storyapp2.data.response.DetailStoriesResponse
import com.example.storyapp2.data.response.Story
import com.example.storyapp2.data.local.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val pref: UserPreference) : ViewModel() {

    private val _story = MutableLiveData<Story>()
    val story: LiveData<Story> = _story
}