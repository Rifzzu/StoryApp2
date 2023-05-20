package com.example.storyapp2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp2.data.local.UserPreference
import com.example.storyapp2.ui.detail.DetailViewModel
import com.example.storyapp2.ui.login.*
import com.example.storyapp2.ui.maps.MapsViewModel
import com.example.storyapp2.ui.upload.AddStoryViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java)-> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java)->{
                DetailViewModel(pref) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java)->{
                AddStoryViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java)->{
                MapsViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}