package com.eniola.albumlistapp.repository

import com.eniola.albumlistapp.repository.database.AppRoomDatabase
import javax.inject.Inject


class AlbumRepository @Inject constructor(
    private val database: AppRoomDatabase
) {

    suspend fun fetchAllAlbums(): List<AlbumData>{
        return database.albumDao().fetchAllAlbums()
    }

    suspend fun deleteAlbum(albumId: Int) {
        return database.albumDao().deleteAlbum(albumId)
    }

    suspend fun createNewAlbum(newAlbum: AlbumData) {
        return database.albumDao().createNewAlbum(newAlbum)
    }

    suspend fun insertIntoAlbums(allAlbums: List<AlbumData>) {
        return database.albumDao().insertAllUsers(allAlbums)
    }

}