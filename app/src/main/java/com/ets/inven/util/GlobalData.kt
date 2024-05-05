package com.ets.inven.util

import android.content.Context
import com.ets.inven.models.AdModel
import com.ets.inven.models.CompanyModel

object GlobalData {
    val COMPANIES = arrayListOf<CompanyModel>(
        CompanyModel("VOLI d.o.o.", "voli@voli.me", "067 111 111", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ", "http://84.247.177.105/voli.png"),
    )

    val PLACEHOLDER_ADS = arrayListOf<AdModel>(
        AdModel("Magacioner", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ", 5, "http://84.247.177.105/magacioner.jpg", COMPANIES[0]),
        AdModel("Programer", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 4, "http://84.247.177.105/programer.jpg", COMPANIES[0]),
        AdModel("Elektroinženjer", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ", 7, "http://84.247.177.105/elektroinzenjer.jpg", COMPANIES[0]),
        AdModel("Profesor", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ", 12, "http://84.247.177.105/profesor.jpg", COMPANIES[0]),
    )

    private var _token: String? = null

    fun loadTokenFromSharedPrefs(content: Context) {
        val sharedPrefs = content.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        _token = sharedPrefs.getString("token", null)
    }

    fun saveToken(content: Context, value: String?) {
        _token = value

        val sharedPrefs = content.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        if (value == null) {
            editor.remove("token")
        } else {
            editor.putString("token", value)
        }
        editor.commit()
    }

    fun setToken(value: String?) {
        _token = value
    }

    fun getToken(): String? {
        return _token
    }

    val API_PREFIX = "http://192.168.1.55:8000/api/"
    val STORAGE_PREFIX = "http://192.168.1.55:8000/storage/"
}