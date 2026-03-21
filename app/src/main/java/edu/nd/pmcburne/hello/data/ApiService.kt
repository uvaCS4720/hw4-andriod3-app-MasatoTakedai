package edu.nd.pmcburne.hello.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface LocationApi {
    @GET("placemarks.json")
    suspend fun getLocations(): List<LocationDto>
}

object RetrofitInstance {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cs.virginia.edu/~wxt4gm/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(LocationApi::class.java)
}

data class LocationDto(
    val id: Int,
    val name: String,
    val tag_list: List<String>,
    val description: String,
    val visual_center: VisualCenter
)

data class VisualCenter(
    val latitude: Double,
    val longitude: Double
)