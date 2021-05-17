package com.eniola.albumlistapp.repository.database

import android.content.Context
import androidx.room.*
import com.eniola.albumlistapp.BuildConfig
import com.eniola.albumlistapp.repository.AlbumData

@Database(entities = [AlbumData::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase: RoomDatabase()  {
    companion object {
        private val DATABASE_NAME =
            if(BuildConfig.DEBUG) "album_list_db" else "album_list_db@@@.db"
        private var sInstance: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase? {
            if(sInstance == null){
               synchronized(AppRoomDatabase::class.java){
                   sInstance = Room.databaseBuilder(context,
                       AppRoomDatabase::class.java, DATABASE_NAME
                   )
                       .build()
               }
            }
            return sInstance
        }
    }

    abstract fun albumDao(): AlbumDao

}