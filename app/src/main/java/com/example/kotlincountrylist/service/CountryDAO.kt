package com.example.kotlincountrylist.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlincountrylist.model.Country

@Dao
interface CountryDAO {

    // Data Access Objects (DAO) methods would be defined here for database operations

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>
    // Insert -> INSERT INTO
    // suspend -> Coroutine, pause & resume
    // vararg -> variable number of arguments
    // List<Long> -> primary keys of inserted rows

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()
}