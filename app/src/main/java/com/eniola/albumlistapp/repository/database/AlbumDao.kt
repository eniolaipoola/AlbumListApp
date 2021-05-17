package com.eniola.albumlistapp.repository.database

import androidx.room.*
import com.eniola.albumlistapp.repository.AlbumData

/**
 * Copyright (c) 2021 Eniola Ipoola
 * All rights reserved
 * Created on 05-May-2021
 */

@Dao
interface AlbumDao {

    @Query("select * from AlbumData order by title asc")
    suspend fun fetchAllAlbums(): List<AlbumData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(userList: List<AlbumData>)

    @Query("select * from AlbumData where id=:id")
    suspend fun fetchAlbumById(id: Int): AlbumData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewAlbum(album: AlbumData)

    @Query("DELETE from AlbumData where id=:albumId")
    suspend fun deleteAlbum(albumId: Int)
}