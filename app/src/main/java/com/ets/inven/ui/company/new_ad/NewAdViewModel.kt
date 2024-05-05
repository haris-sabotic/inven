package com.ets.inven.ui.company.new_ad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ets.inven.api.Api
import com.ets.inven.models.AdPreviewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewAdViewModel : ViewModel() {
    private val _done = MutableLiveData<Boolean>(false)
    val done: LiveData<Boolean> = _done

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun createAd(
         token: String,
         type: String,
         name: String,
         description: String,
         maxAvailablePositions: Int,
         photo: MultipartBody.Part?
    ) {
        Api.service.createAd(
            "Bearer $token",
            type.toRequestBody("text/plain".toMediaTypeOrNull()),
            name.toRequestBody("text/plain".toMediaTypeOrNull()),
            description.toRequestBody("text/plain".toMediaTypeOrNull()),
            maxAvailablePositions.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            photo
        ).enqueue(object :
            Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    _done.postValue(true)
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }
}