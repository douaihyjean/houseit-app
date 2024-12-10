import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a279project.Property
import com.example.a279project.PropertyDetailsActivity
import com.example.a279project.R
import com.example.a279project.Saved
import com.example.a279project.SavedCreate
import com.example.a279project.api.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropertyAdapter(
    private val propertyList: MutableList<Property>, // Original property list
    private val context: Context,
    private val onUnlike: ((Property) -> Unit)? = null // Optional callback for unliking
) : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var filteredList: MutableList<Property> = propertyList.toMutableList() // Filtered property list

    inner class PropertyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profilePicture: ImageView = view.findViewById(R.id.profilePicture) // User's profile picture
        val propertyImage: ImageView = view.findViewById(R.id.propertyImage)  // Property image
        val profileName: TextView = view.findViewById(R.id.profileName)        // User's full name
        val price: TextView = view.findViewById(R.id.price)                    // Property price
        val location: TextView = view.findViewById(R.id.location)              // Property address/location
        val heartIcon: ImageView = view.findViewById(R.id.heartIcon)           // Heart icon for save/remove
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.property_item, parent, false)
        return PropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = filteredList[position]
        val userId = firebaseAuth.currentUser?.uid

        // Bind property data to views
        Glide.with(context)
            .load(property.profilePicture) // Load profile image
            .placeholder(R.drawable.ic_profile_image) // Fallback image
            .into(holder.profilePicture)

        Glide.with(context)
            .load(property.propertyImage) // Load property image
            .placeholder(R.drawable.koraytem) // Fallback image
            .into(holder.propertyImage)

        holder.profileName.text = property.profileName // User's full name
        holder.price.text = "$${property.price}"       // Property price
        holder.location.text = property.address        // Property address

        // Set initial heart icon state
        holder.heartIcon.setImageResource(R.drawable.ic_heart_empty)

        // Handle heart icon click
        holder.heartIcon.setOnClickListener {
            if (userId == null) {
                Toast.makeText(context, "Please log in to save listings.", Toast.LENGTH_SHORT).show()
            } else {
                saveListing(userId, property.id ?: 0, holder.heartIcon)
            }
        }

        // Set click listener to navigate to PropertyDetailsActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PropertyDetailsActivity::class.java).apply {
                putExtra("PROFILE_PICTURE", property.profilePicture)
                putExtra("PROPERTY_IMAGE", property.propertyImage)
                putExtra("PROFILE_NAME", property.profileName)
                putExtra("PRICE", property.price)
                putExtra("ADDRESS", property.address)
                putExtra("DESCRIPTION", property.description)
                putExtra("AREA", property.area)
                putExtra("BEDROOMS", property.bedrooms)
                putExtra("BATHROOMS", property.bathrooms)
                putExtra("STORIES", property.stories)
                putExtra("MAINROAD", property.mainroad)
                putExtra("GUESTROOM", property.guestroom)
                putExtra("FURNISHING_STATUS", property.furnishingStatus)
                putExtra("TITLE", property.title)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = filteredList.size

    /**
     * Updates the property list with the filtered data and refreshes the RecyclerView.
     */
    fun updateList(newList: List<Property>) {
        filteredList.clear()
        filteredList.addAll(newList)
        notifyDataSetChanged()
    }

    /**
     * Resets the filtered list to show all properties.
     */
    fun resetFilter() {
        filteredList.clear()
        filteredList.addAll(propertyList)
        notifyDataSetChanged()
    }

    /**
     * Save the listing to the user's saved listings.
     */
    private fun saveListing(userId: String, listingId: Int, heartIcon: ImageView) {
        val savedRequest = SavedCreate(user_id = userId, listing_id = listingId)
        RetrofitInstance.api.saveListing(savedRequest).enqueue(object : Callback<Saved> {
            override fun onResponse(call: Call<Saved>, response: Response<Saved>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Listing saved!", Toast.LENGTH_SHORT).show()
                    heartIcon.setImageResource(R.drawable.ic_saved)
                } else {
                    Toast.makeText(context, "Failed to save listing. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Saved>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
