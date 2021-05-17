package com.eniola.albumlistapp.repository.remote

import com.eniola.albumlistapp.repository.AlbumData
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

interface APIService {

    @GET("albums")
    suspend fun getAlbums(): List<AlbumData>


}

//provide apiService instance for viewModel classes
class NetworkService @Inject constructor(private val retrofit: Retrofit){
    val apiService: APIService
        get() =
        retrofit.create(APIService::class.java)
}