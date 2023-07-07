package com.alitvinova.countriesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alitvinova.countriesapp.navigation.CountriesListDestination
import com.alitvinova.countriesapp.navigation.CountryInfoDestination
import com.alitvinova.countriesapp.navigation.NavigationDestination
import com.alitvinova.countriesapp.presentation.info.CountryInfoScreen
import com.alitvinova.countriesapp.presentation.info.CountryInfoViewModel
import com.alitvinova.countriesapp.presentation.list.CountriesListScreen
import com.alitvinova.countriesapp.presentation.list.CountriesListViewModel
import com.alitvinova.countriesapp.ui.theme.CountriesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountriesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppNavHost()
                }
            }
        }
    }

    @Composable
    fun MyAppNavHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        startDestination: String = CountriesListDestination.route(),
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            destinations(navController).forEach { entry ->
                val destination = entry.key
                composable(
                    route = destination.route(),
                    arguments = destination.arguments,
                ) {
                    entry.value()
                }
            }
        }
    }

    private fun destinations(
        navController: NavController,
    ) = mapOf<NavigationDestination, @Composable () -> Unit>(
        CountriesListDestination to {
            CountriesListScreen(
                viewModel = viewModel(factory = CountriesListViewModel.Factory),
                navController = navController
            )
        },
        CountryInfoDestination to {
            CountryInfoScreen(
                viewModel = viewModel(factory = CountryInfoViewModel.Factory),
                navController = navController,
            )
        }
    )
}
