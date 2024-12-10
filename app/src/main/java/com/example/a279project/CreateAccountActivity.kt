package com.example.a279project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth
    private val client = OkHttpClient() // HTTP client for API calls

    override fun onStart() {
        super.onStart()

        // Check if the user is already signed in
        if (::auth.isInitialized) {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Find views by their IDs
        val editTextName: TextInputEditText = findViewById(R.id.profileName)
        val editTextEmail: TextInputEditText = findViewById(R.id.inputEmail)
        val editTextPassword: TextInputEditText = findViewById(R.id.inputPassword)
        val editTextConfirmPassword: TextInputEditText = findViewById(R.id.inputConfirmPassword)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val textView: TextView = findViewById(R.id.loginNow)
        registerButton = findViewById(R.id.registerButton)

        // Navigate back to LoginActivity when "Login Now" is clicked
        textView.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Handle registration logic
        registerButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val name = editTextName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val confirmPassword = editTextConfirmPassword.text.toString().trim()

            // Input validation
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || password != confirmPassword) {
                Toast.makeText(this, "Please check your inputs.", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                return@setOnClickListener
            }

            // Firebase Authentication: Create user
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Account creation success
                        val userId = task.result?.user?.uid
                        if (userId != null) {
                            sendUserToBackend(userId, name)
                        } else {
                            Toast.makeText(this, "Error retrieving user ID.", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        // Account creation failure
                        Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // Navigate back to LoginActivity when Back button is clicked
        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun sendUserToBackend(userId: String, fullName: String) {
        // Prepare the JSON payload
        val json = JSONObject()
        json.put("user_id", userId)
        json.put("full_name", fullName)

        // Create the request body
        val requestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        // Prepare the HTTP request
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/users/") // Replace with your FastAPI backend URL
            .post(requestBody)
            .build()

        // Make the API call
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@CreateAccountActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@CreateAccountActivity, "Account created successfully.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@CreateAccountActivity, SearchActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@CreateAccountActivity, "Error saving user to backend.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}
