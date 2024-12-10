package com.example.a279project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a279project.api.RetrofitInstance
import com.example.a279project.Listing
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageListingsActivity : AppCompatActivity() {

    private lateinit var listingsRecyclerView: RecyclerView
    private lateinit var listingsAdapter: ListingsAdapter
    private lateinit var listingsSubtitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_listings)

        // Get reference to the listingsSubtitle TextView
        listingsSubtitle = findViewById(R.id.listingsSubtitle)

        // Fetch user-specific listings from the API
        loadUserListings()

        // Button to navigate to PostActivity
        val addListingButton: Button = findViewById(R.id.addListingButton)
        addListingButton.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

        // Navigation bar icons
        findViewById<ImageView>(R.id.searchIcon).setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        findViewById<ImageView>(R.id.savedIcon).setOnClickListener {
            startActivity(Intent(this, SavedActivity::class.java))
        }
        findViewById<ImageView>(R.id.postIcon).setOnClickListener {
            startActivity(Intent(this, PostActivity::class.java))
        }
        findViewById<ImageView>(R.id.predictIcon).setOnClickListener {
            startActivity(Intent(this, PredictActivity::class.java))
        }
        findViewById<ImageView>(R.id.profileIcon).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserListings()
    }

    private fun loadUserListings() {
        // Fetch listings from the API
        RetrofitInstance.api.getListings(0, 10).enqueue(object : Callback<List<Listing>> {
            override fun onResponse(call: Call<List<Listing>>, response: Response<List<Listing>>) {
                if (response.isSuccessful) {
                    val listings = response.body()?.toMutableList() ?: mutableListOf()

                    // Update the listingsSubtitle TextView with the count of listings
                    val listingCount = listings.size
                    listingsSubtitle.text = "You have ($listingCount) listings"

                    // Initialize RecyclerView and Adapter
                    listingsRecyclerView = findViewById(R.id.listingsRecyclerView)
                    listingsRecyclerView.layoutManager = LinearLayoutManager(this@ManageListingsActivity)
                    listingsAdapter = ListingsAdapter(listings, { listing ->
                        // Handle delete functionality via API
                        deleteListing(listing.id ?: throw IllegalStateException("Listing ID cannot be null"))
                    }, { listing ->
                        // Handle edit functionality: Navigate to PostActivity
                        val intent = Intent(this@ManageListingsActivity, PostActivity::class.java).apply {
                            putExtra("listingId", listing.id ?: 0) // Provide a default ID if null
                            putExtra("title", listing.title)
                            putExtra("price", listing.price)
                            putExtra("address", listing.address)
                            putExtra("description", listing.description)
                            putExtra("area", listing.area)
                            putExtra("bedrooms", listing.bedrooms)
                            putExtra("bathrooms", listing.bathrooms)
                            putExtra("stories", listing.stories)
                            putExtra("mainroad", listing.mainroad)
                            putExtra("guestroom", listing.guestroom)
                            putExtra("basement", listing.basement)
                            putExtra("hot_water_heating", listing.hotWaterHeating)
                            putExtra("air_conditioning", listing.airConditioning)
                            putExtra("parking", listing.parking)
                            putExtra("preferred_area", listing.preferredArea)
                            putExtra("furnishing_status", listing.furnishingStatus)
                            putExtra("image_uri", listing.imageUri)
                        }
                        startActivity(intent)
                    })
                    listingsRecyclerView.adapter = listingsAdapter
                } else {
                    Toast.makeText(this@ManageListingsActivity, "Failed to fetch listings", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Listing>>, t: Throwable) {
                Toast.makeText(this@ManageListingsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteListing(listingId: Int) {
        RetrofitInstance.api.deleteListing(listingId).enqueue(object : Callback<Listing> {
            override fun onResponse(call: Call<Listing>, response: Response<Listing>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ManageListingsActivity, "Listing deleted successfully!", Toast.LENGTH_SHORT).show()
                    loadUserListings() // Reload the listings to refresh count and UI
                } else {
                    Toast.makeText(this@ManageListingsActivity, "Failed to delete listing", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Listing>, t: Throwable) {
                Toast.makeText(this@ManageListingsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

// Updated ListingsAdapter class
class ListingsAdapter(
    private val listings: MutableList<Listing>,
    private val onDelete: (Listing) -> Unit,
    private val onEdit: (Listing) -> Unit
) : RecyclerView.Adapter<ListingsAdapter.ListingViewHolder>() {

    inner class ListingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listingImage: ImageView = view.findViewById(R.id.listingImage)
        val listingTitle: TextView = view.findViewById(R.id.listingTitle)
        val editIcon: ImageView = view.findViewById(R.id.editIcon)
        val deleteIcon: ImageView = view.findViewById(R.id.deleteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listing_item, parent, false)
        return ListingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val listing = listings[position]
        holder.listingTitle.text = listing.title
        // Update with image if applicable
        // holder.listingImage.setImageResource(listing.imageResId) // Uncomment if images are available

        // Handle delete functionality
        holder.deleteIcon.setOnClickListener {
            onDelete(listing)
            listings.removeAt(position)
            notifyItemRemoved(position)
        }

        // Handle edit functionality
        holder.editIcon.setOnClickListener {
            onEdit(listing)
        }
    }

    override fun getItemCount() = listings.size
}