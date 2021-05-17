package com.eniola.albumlistapp.ui.album

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eniola.albumlistapp.repository.AlbumData
import com.eniola.albumlistapp.repository.AlbumRepository
import com.eniola.albumlistapp.repository.remote.NetworkService
import com.eniola.albumlistapp.repository.remote.ResultWrapper
import com.eniola.albumlistapp.utility.runIO
import com.eniola.albumlistapp.repository.remote.safeAPICall
import kotlinx.coroutines.Job
import javax.inject.Inject

/**
 * Copyright (c) 2021 Eniola Ipoola
 * All rights reserved
 * Created on 17-May-2021
 */

class AlbumViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val albumRepository: AlbumRepository
) : ViewModel() {

    val state = MutableLiveData<ViewState>()
    private val job = Job()

    fun cancelJob() {
        job.cancel()
    }

    fun fetchAlbumsFromApi() {
        state.postValue(ViewState.LOADING(true))
        runIO {
            when (val getAlbums = safeAPICall {
                networkService.apiService.getAlbums()
            }) {
                //api call is successful, save data into database
                is ResultWrapper.Success -> when (
                    val localAlbums = safeAPICall {
                        val allAlbums = getAlbums.value
                        allAlbums.let { albumRepository.insertIntoAlbums(it) }
                    }) {

                    is ResultWrapper.Success -> {
                        state.postValue(ViewState.LOADING(false))
                        //fetch all albums from database
                        fetchAlbumsFromDatabase()
                    }

                    is ResultWrapper.Error -> {
                        //notify fragment of error
                        state.postValue(ViewState.LOADING(false))
                        state.postValue(localAlbums.errorMessage?.let {
                            ViewState.ERROR(
                                it
                            )
                        })

                    }
                }

                //api called failed
                is ResultWrapper.Error -> {
                    state.postValue(ViewState.LOADING(false))
                    state.postValue(getAlbums.errorMessage?.let { ViewState.ERROR(it) })
                }
            }
        }
    }

    fun fetchAlbumsFromDatabase() {
        state.postValue(ViewState.LOADING(true))
        runIO {
            //fetch user from database
            when (val fetchAlbums = safeAPICall {
                albumRepository.fetchAllAlbums()
            }) {
                is ResultWrapper.Success -> {
                    //successfully fetched from database
                    val allAlbums = fetchAlbums.value
                    state.postValue(ViewState.LOADING(false))
                    state.postValue(
                        ViewState.SUCCESS(
                            "Albums fetched successfully", allAlbums
                        )
                    )
                }

                is ResultWrapper.Error -> {
                    state.postValue(fetchAlbums.errorMessage?.let { ViewState.ERROR(it) })
                    state.postValue(ViewState.LOADING(false))
                }
            }
        }
    }

}

sealed class ViewState {
    data class SUCCESS(val message: String, val data: List<AlbumData>) : ViewState()
    data class LOADING(val loading: Boolean = false) : ViewState()
    data class ERROR(val errorMessage: String) : ViewState()

}