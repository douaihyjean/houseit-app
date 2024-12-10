import com.example.a279project.Listing
import com.example.a279project.ListingCreate
import com.example.a279project.Property
import com.example.a279project.Saved
import com.example.a279project.SavedCreate
import retrofit2.Call
import retrofit2.http.*

interface ListingsApiService {
    @GET("listings/")
    fun getListings(@Query("skip") skip: Int, @Query("limit") limit: Int): Call<List<Listing>>

    @POST("listings/")
    fun createListing(@Body listing: ListingCreate): Call<Listing>

    @GET("listings/{id}")
    fun getListingById(@Path("id") id: Int): Call<Listing>

    @PUT("listings/{id}")
    fun updateListing(@Path("id") id: Int, @Body listing: ListingCreate): Call<Listing>

    @DELETE("listings/{id}")
    fun deleteListing(@Path("id") id: Int): Call<Listing>

    @GET("users/{user_id}/full_name")
    fun getUserFullName(@Path("user_id") userId: String): Call<String>

    @GET("listings/")
    fun getUserListings(@Query("user_id") userId: String): Call<List<Listing>>

    @POST("saved/")
    fun saveListing(@Body saved: SavedCreate): Call<Saved>

    @GET("saved/{user_id}")
    fun getSavedListings(@Path("user_id") userId: String): Call<List<Property>>
}
