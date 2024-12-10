package com.example.a279project

import PropertyAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a279project.api.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SavedActivity : AppCompatActivity() {

    private lateinit var savedAdapter: PropertyAdapter
    private lateinit var savedRecyclerView: RecyclerView
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.saved_page)

        // Initialize RecyclerView
        savedRecyclerView = findViewById(R.id.savedRecyclerView)
        savedRecyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch saved properties for the current user
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            fetchSavedProperties(userId)
        } else {
            // Handle case when the user is not logged in
            Toast.makeText(this, "Please log in to view saved properties.", Toast.LENGTH_SHORT).show()
        }

        // Set up bottom navigation
        setupBottomNavigation()
    }

    /**
     * Fetch saved properties from the API for a specific user.
     */
    private fun fetchSavedProperties(userId: String) {
        RetrofitInstance.api.getSavedListings(userId).enqueue(object : Callback<List<Property>> {
            override fun onResponse(call: Call<List<Property>>, response: Response<List<Property>>) {
                if (response.isSuccessful) {
                    val savedProperties = response.body()?.toMutableList() ?: mutableListOf()

                    if (savedProperties.isEmpty()) {
                        Toast.makeText(this@SavedActivity, "No saved properties found.", Toast.LENGTH_SHORT).show()
                    }

                    // Set up the adapter with saved properties
                    savedAdapter = PropertyAdapter(savedProperties, this@SavedActivity) { property ->
                        // Remove property from the list dynamically
                        savedProperties.remove(property)
                        savedAdapter.notifyDataSetChanged()
                    }
                    savedRecyclerView.adapter = savedAdapter
                } else {
                    Toast.makeText(this@SavedActivity, "Failed to fetch saved properties.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Property>>, t: Throwable) {
                Toast.makeText(this@SavedActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Set up bottom navigation icons and their actions.
     */
    private fun setupBottomNavigation() {
        val searchIcon: ImageView = findViewById(R.id.searchIcon)
        val profileIcon: ImageView = findViewById(R.id.profileIcon)
        val predictIcon: ImageView = findViewById(R.id.predictIcon)
        val postIcon: ImageView = findViewById(R.id.postIcon)

        searchIcon.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        profileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        predictIcon.setOnClickListener {
            startActivity(Intent(this, PredictActivity::class.java))
        }
        postIcon.setOnClickListener {
            startActivity(Intent(this, ManageListingsActivity::class.java))
        }
    }
}
