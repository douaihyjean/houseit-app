package com.example.a279project

import PropertyAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a279project.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var propertyAdapter: PropertyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_screen) // Ensure this layout is correct

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.propertyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize PropertyAdapter with an empty list and set it to RecyclerView
        propertyAdapter = PropertyAdapter(mutableListOf(), this)
        recyclerView.adapter = propertyAdapter

        // Fetch listings from the API
        fetchListingsFromApi()

        // Set up search bar
        val searchBar = findViewById<EditText>(R.id.searchField)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase()
                val filteredList = propertyAdapter.filteredList.filter {
                    it.address.lowercase().contains(query) ||
                            it.price.lowercase().contains(query) ||
                            it.profileName.lowercase().contains(query)
                }
                propertyAdapter.updateList(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Bottom Navigation Icons Click Listeners
        val profileIcon = findViewById<ImageView>(R.id.profileIcon)
        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(intent)
        }

        val savedIcon = findViewById<ImageView>(R.id.savedIcon)
        savedIcon.setOnClickListener {
            val intent = Intent(this, SavedActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(intent)
        }

        val searchIcon = findViewById<ImageView>(R.id.searchIcon)
        searchIcon.setOnClickListener {
            // Already on SearchActivity, no action needed
        }

        val predictIcon: ImageView = findViewById(R.id.predictIcon)
        predictIcon.setOnClickListener {
            val intent = Intent(this, PredictActivity::class.java)
            startActivity(intent)
        }

        val postIcon: ImageView = findViewById(R.id.postIcon)
        postIcon.setOnClickListener {
            val intent = Intent(this, ManageListingsActivity::class.java)
            startActivity(intent)
        }

        val filterIcon: ImageView = findViewById(R.id.filterIcon)
        filterIcon.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivityForResult(intent, 1001)
        }
    }

    private fun fetchListingsFromApi() {
        val apiService = RetrofitInstance.api

        apiService.getListings(skip = 0, limit = 50).enqueue(object : Callback<List<Listing>> {
            override fun onResponse(call: Call<List<Listing>>, response: Response<List<Listing>>) {
                if (response.isSuccessful && response.body() != null) {
                    val propertyList = response.body()!!.map { listing ->
                        Property(
                            profilePicture = R.drawable.ic_profile_image, // Replace with default profile image
                            propertyImage = R.drawable.koraytem, // Placeholder image for property
                            profileName = listing.userFullName,
                            price = listing.price,
                            address = listing.address,
                            description = listing.description,
                            area = listing.area,
                            bedrooms = listing.bedrooms,
                            bathrooms = listing.bathrooms,
                            stories = listing.stories,
                            mainroad = listing.mainroad,
                            guestroom = listing.guestroom,
                            basement = listing.basement,
                            hotWaterHeating = listing.hotWaterHeating,
                            airConditioning = listing.airConditioning,
                            parking = listing.parking.toString(),
                            preferredArea = listing.preferredArea,
                            furnishingStatus = listing.furnishingStatus,
                            title = listing.title,
                            id = listing.id ?: 0                        )
                    }
                    propertyAdapter.updateList(propertyList)
                } else {
                    Toast.makeText(this@SearchActivity, "Failed to fetch listings", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Listing>>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@SearchActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
