package com.example.storyapp2.ui.main

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.*
import com.example.storyapp2.data.api.ApiConfig
import com.example.storyapp2.data.response.ListStoryItem
import com.example.storyapp2.data.response.StoryRepository
import com.example.storyapp2.utils.Injection

class MainViewModel(storyRepository: StoryRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    val stories : LiveData<PagingData<ListStoryItem>> =
        storyRepository.getAllStories().cachedIn(viewModelScope)

    class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(Injection.provideRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}