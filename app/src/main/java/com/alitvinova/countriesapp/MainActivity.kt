package com.alitvinova.countriesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.alitvinova.countriesapp.coutrieslist.presentation.CountriesListScreen
import com.alitvinova.countriesapp.coutrieslist.presentation.CountriesListViewModel
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
                    CountriesListScreen(CountriesListViewModel((application as App).repository))
                }
            }
        }
    }
}
