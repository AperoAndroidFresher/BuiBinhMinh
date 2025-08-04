package com.example.buibinhminh.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.buibinhminh.database.converter.UriConverter
import com.example.buibinhminh.database.dao.PlaylistDao
import com.example.buibinhminh.database.dao.ProfileDao
import com.example.buibinhminh.database.dao.SongDao
import com.example.buibinhminh.database.dao.UserDao
import com.example.buibinhminh.database.entity.PlaylistEntity
import com.example.buibinhminh.database.entity.PlaylistSongEntity
import com.example.buibinhminh.database.entity.ProfileEntity
import com.example.buibinhminh.database.entity.SongEntity
import com.example.buibinhminh.database.entity.UserEntity

@Database(
    entities = [UserEntity::class, ProfileEntity::class, PlaylistEntity::class, SongEntity::class, PlaylistSongEntity::class],
    version = 1
)
@TypeConverters(UriConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun songDao(): SongDao
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "music_app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}