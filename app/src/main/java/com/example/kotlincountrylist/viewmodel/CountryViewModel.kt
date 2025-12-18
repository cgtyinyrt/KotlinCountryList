package com.example.kotlincountrylist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.kotlincountrylist.model.Country

class CountryViewModel(application: Application) : BaseViewModel(application) {

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