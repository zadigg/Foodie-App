package com.example.fodiepal.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.fodiepal.MainActivity
import com.example.fodiepal.R
import com.example.fodiepal.home.HomeActivity


class AuthenticationActivity : AppCompatActivity() {
    private val usersList = ArrayList<User>()
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authentication_activity)

        usersList.add(User("abc", "abc", "abc@abc.com", "abc"))
        usersList.add(User("a", "a", "a", "a"))
        usersList.add(User("Bob", "Johnson", "bob@example.com", "mySecretPwd"))
        usersList.add(User("Eve", "Adams", "eve@example.com", "123456"))
        usersList.add(User("Charlie", "Brown", "charlie@example.com", "brownie"))

        val usernameEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.btnLogin)

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            navigateToHome()
        }

        loginButton.setOnClickListener {
            val enteredUsername = usernameEditText.text.toString().trim()
            val enteredPassword = passwordEditText.text.toString().trim()

            val userFound = usersList.find { it.username == enteredUsername && it.password == enteredPassword }

            if (userFound != null) {
                saveLoginStatus(true, enteredUsername)
                navigateToHome()
            } else {
                val message = "Login failed. Invalid username or password."
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun saveLoginStatus(isLoggedIn: Boolean, username: String) {
        val editor = sharedPref.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.putString("username", username)
        editor.apply()
    }
}
