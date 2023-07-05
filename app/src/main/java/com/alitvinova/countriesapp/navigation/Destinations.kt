package com.alitvinova.countriesapp.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object CountriesListDestination : NavigationDestination {
    override fun route() = "countries_list"
}

object CountryInfoDestination : NavigationDestination {
    private const val ROUTE = "country_info"
    const val CODE_ARG = "code"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(CODE_ARG) { type = NavType.StringType }
    )

    override fun route(): String = "$ROUTE/{$CODE_ARG}"
    fun createRoute(code: String) = "$ROUTE/$code"
}