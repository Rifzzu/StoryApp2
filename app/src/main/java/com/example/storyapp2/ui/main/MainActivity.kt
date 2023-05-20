package com.example.storyapp2.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp2.R
import com.example.storyapp2.adapter.LoadingStateAdapter
import com.example.storyapp2.adapter.StoryAdapter
import com.example.storyapp2.databinding.ActivityMainBinding
import com.example.storyapp2.ui.login.LoginActivity
import com.example.storyapp2.ui.maps.MapsActivity
import com.example.storyapp2.ui.upload.AddStoryActivity
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.ViewModelFactory(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var pref : com.example.storyapp2.data.UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = com.example.storyapp2.data.UserPreference(this)
        val token: String = intent.getStringExtra("TOKEN").toString()
        setRecycleView(token)

        mainViewModel.loading.observe(this) { loading ->
            showLoading(loading)
        }
        navigateToUploadActivity()
    }

    private fun navigateToUploadActivity() {
        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    private fun setRecycleView(token: String) {
        val adapter = StoryAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        binding.rvStory.adapter = adapter
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.stories.observe(this){
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                lifecycleScope.launch {
                    pref.clearToken()
                }
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                true
            }
            R.id.maps -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                true
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoadingScreen.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}