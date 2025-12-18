package com.example.kotlincountrylist.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlincountrylist.model.Country

@Database(entities = [Country::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDAO() : CountryDAO

    // Singleton pattern for database instance can be implemented here if needed
    companion object {
        // If the instance is not null, then return it, if it is, then create the database.
        @Volatile
        private var instance: CountryDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,
            CountryDatabase::class.java,
            "country_database"
        ).build()

        /*@Volatile
        private var INSTANCE: CountryDatabase? = null

        fun getDatabase(context: android.content.Context): CountryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    CountryDatabase::class.java,
                    "country_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

         */
    }
}