package com.ets.inven.ui.company.ad_applications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ets.inven.api.Api
import com.ets.inven.api.responses.ErrorResponse
import com.ets.inven.models.AdPreviewModel
import com.ets.inven.models.UserModel
import com.ets.inven.util.deserializeJson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdApplicationsCompanyViewModel : ViewModel() {
    private val _applications = MutableLiveData<ArrayList<UserModel>>()
    val applications: LiveData<ArrayList<UserModel>> = _applications

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadApplications(token: String, adId: Int) {
        Api.service.adApplications("Bearer $token", adId).enqueue(object :
            Callback<ArrayList<UserModel>> {
            override fun onResponse(
                call: Call<ArrayList<UserModel>>,
                response: Response<ArrayList<UserModel>>
            ) {
                if (response.isSuccessful) {
                    _applications.postValue(response.body()!!)
                } else {
                    val body = deserializeJson<ErrorResponse<Any>>(response.errorBody()!!.string())
                    _errorMessage.postValue(body.message)
                }
            }

            override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }
}