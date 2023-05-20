package com.example.storyapp2.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp2.data.local.UserPreference
import com.example.storyapp2.ui.main.MainActivity
import com.example.storyapp2.databinding.ActivitySplashBinding
import com.example.storyapp2.ui.login.LoginActivity
import com.example.storyapp2.ui.login.LoginViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.stateData().observe(this) {
            token = it
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (token.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("TOKEN", token)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 2000)
        logoAnimation()
    }

    private fun logoAnimation() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_Y, 0f, 320f).apply {
            duration = 1500
        }.start()

        ObjectAnimator.ofFloat(binding.logo, View.ALPHA, 0f, 1f).apply {
            duration = 1500
        }.start()

        ObjectAnimator.ofFloat(binding.logo, View.SCALE_X, 0f, 1f).apply {
            duration = 1500
        }.start()

        ObjectAnimator.ofFloat(binding.logo, View.SCALE_Y, 0f, 1f).apply {
            duration = 1500
        }.start()
    }
}