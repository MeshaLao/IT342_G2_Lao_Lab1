package com.lao.userauth.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.lao.userauth.R
import com.lao.userauth.repository.AuthRepository
import com.lao.userauth.utils.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var registerButton: Button
    private lateinit var goToLogin: TextView
    private lateinit var progressBar: ProgressBar

    private val repository = AuthRepository()
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tokenManager = TokenManager(this)

        usernameInput = findViewById(R.id.usernameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        registerButton = findViewById(R.id.registerButton)
        goToLogin = findViewById(R.id.goToLogin)
        progressBar = findViewById(R.id.progressBar)

        registerButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            performRegister(username, email, password)
        }

        goToLogin.setOnClickListener {
            finish()
        }
    }

    private fun performRegister(username: String, email: String, password: String) {
        progressBar.visibility = View.VISIBLE
        registerButton.isEnabled = false

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.register(username, email, password)

                withContext(Dispatchers.Main) {
                    tokenManager.saveToken(response.token)
                    Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT).show()
                    navigateToDashboard()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                    registerButton.isEnabled = true
                }
            }
        }
    }

    private fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}