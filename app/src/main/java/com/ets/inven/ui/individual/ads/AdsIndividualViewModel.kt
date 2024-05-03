package com.ets.inven.ui.individual.ads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ets.inven.api.Api
import com.ets.inven.models.AdPreviewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdsIndividualViewModel : ViewModel() {
    private val _ads = MutableLiveData<ArrayList<AdPreviewModel>>()
    val ads: LiveData<ArrayList<AdPreviewModel>> = _ads

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadAds(type: String?) {
        Api.service.allAds(type).enqueue(object : Callback<ArrayList<AdPreviewModel>> {
            override fun onResponse(
                call: Call<ArrayList<AdPreviewModel>>,
                response: Response<ArrayList<AdPreviewModel>>
            ) {
                if (response.isSuccessful) {
                    _ads.postValue(response.body()!!)
                }
            }

            override fun onFailure(call: Call<ArrayList<AdPreviewModel>>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }
}