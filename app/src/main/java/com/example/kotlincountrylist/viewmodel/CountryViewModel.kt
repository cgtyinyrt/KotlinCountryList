package com.example.kotlincountrylist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountrylist.model.Country

class CountryViewModel : ViewModel() {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom() {
        val country = Country(
            "Japan",
            "Tokyo",
            "Asia",
            "JPY",
            "www.ss.com",
            "Japanese"
        )
        countryLiveData.value = country
    }
}