package com.justnik.weatherapp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.justnik.weatherapp2.data.database.model.CityWeatherDbModel
import com.justnik.weatherapp2.data.database.model.DailyWeatherDbModel

@Database(entities = [
    CityWeatherDbModel::class,
    DailyWeatherDbModel::class],
    version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {

        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

                db = instance
                return instance
            }
        }
    }

    abstract fun cityWeatherDao(): CityWeatherDao
}