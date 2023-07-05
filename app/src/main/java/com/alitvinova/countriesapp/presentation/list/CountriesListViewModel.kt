package com.alitvinova.countriesapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alitvinova.countriesapp.App
import com.alitvinova.countriesapp.data.Repository
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = (this[APPLICATION_KEY] as App).repository
                CountriesListViewModel(repository)
            }
        }
    }
}