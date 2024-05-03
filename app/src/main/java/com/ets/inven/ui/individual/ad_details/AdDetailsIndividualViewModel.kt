package com.ets.inven.ui.individual.ad_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ets.inven.api.Api
import com.ets.inven.models.AdModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdDetailsIndividualViewModel : ViewModel() {
    private val _ad = MutableLiveData<AdModel>()
    val ad: LiveData<AdModel> = _ad

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadAd(adId: Int) {
        Api.service.singleAd(adId).enqueue(object : Callback<AdModel> {
            override fun onResponse(call: Call<AdModel>, response: Response<AdModel>) {
                if (response.isSuccessful) {
                    _ad.postValue(response.body()!!)
                }
            }

            override fun onFailure(call: Call<AdModel>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }
}