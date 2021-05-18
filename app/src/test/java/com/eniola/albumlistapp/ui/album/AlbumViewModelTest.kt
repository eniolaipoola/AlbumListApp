package com.eniola.albumlistapp.ui.album

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eniola.albumlistapp.BuildConfig.API_BASE_URL
import com.eniola.albumlistapp.repository.AlbumRepository
import com.eniola.albumlistapp.repository.database.AppRoomDatabase
import com.eniola.albumlistapp.repository.remote.NetworkService
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Copyright (c) 2021 Eniola Ipoola
 * All rights reserved
 * Created on 18-May-2021
 */

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class AlbumViewModelTest {

    @Mock
    lateinit var albumRepository: AlbumRepository
    @Mock
    lateinit var networkService: NetworkService

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private lateinit var database: AppRoomDatabase

    private lateinit var mockWebServer: MockWebServer

    private lateinit var albumViewModel: AlbumViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()


        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppRoomDatabase::class.java
        ).allowMainThreadQueries().build()

        networkService = NetworkService(retrofit)
        albumRepository = AlbumRepository(database)
        albumViewModel = AlbumViewModel(networkService, albumRepository)

    }

    @After
    fun tearDown() {
        database.close()
        mockWebServer.shutdown()
    }

    @Test
    fun fetchAlbumFromDatabase() = runBlocking {
        albumViewModel.fetchAlbumsFromDatabase()
    }

    @Test
    fun fetchAlbumFromAPI() = runBlocking {
        val albums = networkService.apiService.getAlbums()
        val expectedAlbumData = MockResponseFileReader("success_response.json")
        assert(albums.isNotEmpty())
        assertNotNull(expectedAlbumData.content)
    }

}