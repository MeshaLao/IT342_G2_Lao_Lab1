package com.lao.userauth.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lao.userauth.R
import com.lao.userauth.repository.AuthRepository
import com.lao.userauth.utils.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity : AppCompatActivity() {

    private lateinit var welcomeText: TextView
    private lateinit var usernameText: TextView
    private lateinit var emailText: TextView
    private lateinit var userIdText: TextView
    private lateinit var logoutButton: Button
    private lateinit var progressBar: ProgressBar

    private val repository = AuthRepository()
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tokenManager = TokenManager(this)

        // Check if logged in
        if (!tokenManager.isLoggedIn()) {
            navigateToLogin()
            return
        }

        welcomeText = findViewById(R.id.welcomeText)
        usernameText = findViewById(R.id.usernameText)
        emailText = findViewById(R.id.emailText)
        userIdText = findViewById(R.id.userIdText)
        logoutButton = findViewById(R.id.logoutButton)
        progressBar = findViewById(R.id.progressBar)

        loadProfile()

        logoutButton.setOnClickListener {
            tokenManager.clearToken()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }
    }

    private fun loadProfile() {
        progressBar.visibility = View.VISIBLE

        val token = tokenManager.getToken()!!

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = repository.getProfile(token)

                withContext(Dispatchers.Main) {
                    welcomeText.text = "Hello, ${user.userName}! 👋"
                    usernameText.text = user.userName
                    emailText.text = user.email
                    userIdText.text = "#${user.userID}"
                    progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@DashboardActivity, "Failed to load profile", Toast.LENGTH_SHORT).show()
                    tokenManager.clearToken()
                    navigateToLogin()
                }
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}