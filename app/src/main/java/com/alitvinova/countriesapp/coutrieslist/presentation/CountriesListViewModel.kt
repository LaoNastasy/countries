package com.alitvinova.countriesapp.coutrieslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alitvinova.countriesapp.coutrieslist.data.Repository
import com.alitvinova.countriesapp.domain.entity.CountryListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountriesListViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow(CountriesListState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onRetryClick() = loadData()

    fun onCountryClick(country: CountryListItem) {}

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(error = null, loading = true) }
            try {
                val countries = repository.getAllCountries()
                _state.update { it.copy(countries = countries) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e) }
            } finally {
                _state.update { it.copy(loading = false) }
            }
        }
    }
}