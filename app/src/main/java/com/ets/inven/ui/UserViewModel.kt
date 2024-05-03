package com.ets.inven.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ets.inven.api.Api
import com.ets.inven.api.requests.EditUserRequest
import com.ets.inven.api.requests.LoginRequest
import com.ets.inven.api.requests.RegisterRequest
import com.ets.inven.api.responses.EditResponse
import com.ets.inven.api.responses.ErrorResponse
import com.ets.inven.api.responses.LoginResponse
import com.ets.inven.api.responses.RegisterResponse
import com.ets.inven.models.UserModel
import com.ets.inven.util.deserializeJson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserViewModel : ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _userData = MutableLiveData<UserModel>()
    val userData: LiveData<UserModel> = _userData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loginErrors = MutableLiveData<LoginErrors>()
    val loginErrors: LiveData<LoginErrors> = _loginErrors

    private val _registerErrors = MutableLiveData<RegisterErrors>()
    val registerErrors: LiveData<RegisterErrors> = _registerErrors

    data class LoginErrors (
        val email: ArrayList<String>?,
        val password: ArrayList<String>?,
    )

    data class RegisterErrors (
        val type: ArrayList<String>?,
        val name: ArrayList<String>?,
        val email: ArrayList<String>?,
        val password: ArrayList<String>?,
    )

    fun loadUserData(token: String) {
        Api.service.user("Bearer $token").enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    _userData.postValue(response.body()!!)
                } else {
                    val body = deserializeJson<ErrorResponse<Any>>(response.errorBody()!!.string())
                    _errorMessage.postValue(body.message)
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }

    fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        Api.service.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    _token.postValue(response.body()!!.token)
                    _userData.postValue(response.body()!!.user)
                } else {
                    val body = deserializeJson<ErrorResponse<LoginErrors>>(response.errorBody()!!.string())
                    _errorMessage.postValue(body.message)
                    _loginErrors.postValue(body.errors!!)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }

    fun register(type: String, name: String, email: String, password: String) {
        val registerRequest = RegisterRequest(type, name, email, password)
        Api.service.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    _token.postValue(response.body()!!.token)
                    _userData.postValue(response.body()!!.user)
                } else {
                    val body = deserializeJson<ErrorResponse<RegisterErrors>>(response.errorBody()!!.string())
                    _errorMessage.postValue(body.message)
                    _registerErrors.postValue(body.errors!!)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }

    fun editPhoto(token: String, photo: MultipartBody.Part) {
        Api.service.editUserPhoto("Bearer $token", photo).enqueue(object : Callback<EditResponse> {
            override fun onResponse(call: Call<EditResponse>, response: Response<EditResponse>) {
                if (response.isSuccessful) {
                    _userData.postValue(response.body()!!.new_user)
                } else {
                    val body = deserializeJson<ErrorResponse<Any>>(response.errorBody()!!.string())
                    _errorMessage.postValue(body.message)
                }
            }

            override fun onFailure(call: Call<EditResponse>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }

    fun editCV(token: String, cv: MultipartBody.Part) {
        Api.service.editUserCV("Bearer $token", cv).enqueue(object : Callback<EditResponse> {
            override fun onResponse(call: Call<EditResponse>, response: Response<EditResponse>) {
                if (response.isSuccessful) {
                    _userData.postValue(response.body()!!.new_user)
                } else {
                    val body = deserializeJson<ErrorResponse<Any>>(response.errorBody()!!.string())
                    _errorMessage.postValue(body.message)
                }
            }

            override fun onFailure(call: Call<EditResponse>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }

    fun edit(token: String, editUserRequest: EditUserRequest) {
        Api.service.editUser("Bearer $token", editUserRequest).enqueue(object : Callback<EditResponse> {
            override fun onResponse(call: Call<EditResponse>, response: Response<EditResponse>) {
                if (response.isSuccessful) {
                    _userData.postValue(response.body()!!.new_user)
                } else {
                    val body = deserializeJson<ErrorResponse<Any>>(response.errorBody()!!.string())
                    _errorMessage.postValue(body.message)
                }
            }

            override fun onFailure(call: Call<EditResponse>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }
}