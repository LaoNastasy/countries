package com.alitvinova.countriesapp.navigation

import androidx.navigation.NamedNavArgument

interface NavigationDestination {

    fun route(): String

    val arguments: List<NamedNavArgument>
        get() = emptyList()
}
