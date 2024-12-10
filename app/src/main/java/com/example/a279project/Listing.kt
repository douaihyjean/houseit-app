package com.example.a279project

import com.google.gson.annotations.SerializedName

data class Listing(
    val id: Int?,                           // Auto-generated ID for the listing

    @SerializedName("title")
    val title: String,                      // Title of the listing

    @SerializedName("price")
    val price: String,                      // Price of the property

    @SerializedName("address")
    val address: String,                    // Address of the property

    @SerializedName("description")
    val description: String,                // Detailed description of the listing

    @SerializedName("area")
    val area: String,                       // Area of the property

    @SerializedName("bedrooms")
    val bedrooms: String,                   // Number of bedrooms

    @SerializedName("bathrooms")
    val bathrooms: String,                  // Number of bathrooms

    @SerializedName("stories")
    val stories: String,                    // Number of stories

    @SerializedName("mainroad")
    val mainroad: String,                   // Whether it's near the main road

    @SerializedName("guestroom")
    val guestroom: String,                  // Whether it includes a guest room

    @SerializedName("basement")
    val basement: String,                   // Whether it includes a basement

    @SerializedName("hot_water_heating")
    val hotWaterHeating: String,            // Hot water heating system

    @SerializedName("air_conditioning")
    val airConditioning: String,            // Air conditioning availability

    @SerializedName("parking")
    val parking: Int,                       // Number of parking spaces

    @SerializedName("preferred_area")
    val preferredArea: String,              // Preferred area for the listing

    @SerializedName("furnishing_status")
    val furnishingStatus: String,           // Furnishing status

    @SerializedName("image_uri")
    val imageUri: String?,                  // URI for the image of the listing

    @SerializedName("user_id")
    val userId: String,                     // User ID who owns the listing

    @SerializedName("user_full_name")
    val userFullName: String                // Full name of the user
)

data class ListingCreate(
    val title: String,              // Title of the listing
    val price: String,              // Price of the property
    val address: String,            // Address of the property
    val description: String,        // Detailed description of the listing
    val area: String,               // Area of the property
    val bedrooms: String,           // Number of bedrooms
    val bathrooms: String,          // Number of bathrooms
    val stories: String,            // Number of stories
    val mainroad: String,           // Whether it's near the main road
    val guestroom: String,          // Whether it includes a guest room
    val basement: String,           // Whether it includes a basement
    val hot_water_heating: String,  // Hot water heating system
    val air_conditioning: String,   // Air conditioning availability
    val parking: Int,               // Number of parking spaces
    val preferred_area: String,     // Preferred area for the listing
    val furnishing_status: String,  // Furnishing status
    val image_uri: String?,         // URI for the image of the listing
    val user_id: String,            // User ID who owns the listing
    val user_full_name: String      // Full name of the user
)
