package com.example.kotlincountrylist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.kotlincountrylist.model.Country
import com.example.kotlincountrylist.service.CountryAPIService
import com.example.kotlincountrylist.service.CountryDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<List<Country>> ()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(countryList: List<Country>) {
        // Implementation for storing data in SQLite database
        launch {
            val dao = CountryDatabase(getApplication()).countryDAO()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*countryList.toTypedArray()) // -> list -> individual
            var i = 0
            while (i < countryList.size) {
                countryList[i].uuid = listLong[i].toInt()
                i += 1
            }
            showCountries(countryList)
        }
    }
}