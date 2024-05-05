package com.ets.inven.ui.individual.ad_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ets.inven.api.Api
import com.ets.inven.api.responses.AppliedResponse
import com.ets.inven.models.AdModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdDetailsIndividualViewModel : ViewModel() {
    private val _ad = MutableLiveData<AdModel>()
    val ad: LiveData<AdModel> = _ad

    private val _applied = MutableLiveData<Boolean>()
    val applied: LiveData<Boolean> = _applied

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

    fun adApply(token: String, adId: Int) {
        Api.service.adApply("Bearer $token", adId).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    _applied.postValue(true)
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }

    fun adApplied(token: String, adId: Int) {
        Api.service.adApplied("Bearer $token", adId).enqueue(object : Callback<AppliedResponse> {
            override fun onResponse(call: Call<AppliedResponse>, response: Response<AppliedResponse>) {
                if (response.isSuccessful) {
                    _applied.postValue(response.body()!!.applied)
                }
            }

            override fun onFailure(call: Call<AppliedResponse>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }
}