package com.ets.inven.util

import com.ets.inven.models.AdModel
import com.ets.inven.models.CompanyModel

object GlobalData {
    val COMPANIES = arrayListOf<CompanyModel>(
        CompanyModel("VOLI d.o.o.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ", "http://84.247.177.105/voli.png"),
    )

    val PLACEHOLDER_ADS = arrayListOf<AdModel>(
        AdModel("Magacioner", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ", 5, "http://84.247.177.105/magacioner.jpg", COMPANIES[0]),
        AdModel("Programer", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 4, "http://84.247.177.105/programer.jpg", COMPANIES[0]),
        AdModel("Elektroinženjer", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ", 7, "http://84.247.177.105/elektroinzenjer.jpg", COMPANIES[0]),
        AdModel("Profesor", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ", 12, "http://84.247.177.105/profesor.jpg", COMPANIES[0]),
    )
}