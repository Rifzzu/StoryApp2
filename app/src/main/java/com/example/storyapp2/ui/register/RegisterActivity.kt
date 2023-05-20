package com.example.storyapp2.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp2.databinding.ActivityRegisterBinding
import com.example.storyapp2.ui.login.LoginActivity


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 8) {
                    registerViewModel.register(
                        name, email, password
                    )
                }
            } else {
                toastMessage("Fill the Form")
            }

        }
        registerViewModel.message.observe(this) { message ->
            toastMessage(message)
            if (message == "User created") {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        registerViewModel.loading.observe(this) { loading ->
            showLoading(loading)
        }
    }

    private fun toastMessage(message: String) {
        if (message == "Bad Request") {
            val changeMessage = "Email is already taken"
            Toast.makeText(this, changeMessage, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}