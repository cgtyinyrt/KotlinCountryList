package com.example.kotlincountrylist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.kotlincountrylist.model.Country
import com.example.kotlincountrylist.service.local.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid : Int) {
        /*val country = Country(
            "Japan",
            "Tokyo",
            "Asia",
            "JPY",
            "www.ss.com",
            "Japanese"

        countryLiveData.value = country
         */

        launch {
            val dao = CountryDatabase(getApplication()).countryDAO()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}