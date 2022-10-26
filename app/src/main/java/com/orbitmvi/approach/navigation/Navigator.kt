package com.orbitmvi.approach.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.orbitmvi.feature.details.navigation.CountryDetailNavigation
import com.orbitmvi.feature.list.navigation.CountryListNavigation
import com.orbitmvi.approach.R

class Navigator : CountryListNavigation, CountryDetailNavigation {
    private var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun unbind() {
        navController = null
    }

    override fun countryDetail(id: Int) {
        val bundle = Bundle()
        bundle.putInt("country_id", id)
        navController?.navigate(R.id.actionCountryDetails, bundle)
    }

    override fun goToCountryList() {
        navController?.popBackStack()
    }
}
