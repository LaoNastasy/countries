package com.alitvinova.countriesapp.presentation.info

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alitvinova.countriesapp.App
import com.alitvinova.countriesapp.data.Repository
import com.alitvinova.countriesapp.navigation.CountryInfoDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountryInfoViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
) : ViewModel() {
    private val code: String = savedStateHandle[CountryInfoDestination.CODE_ARG]!!
    private val _state = MutableStateFlow(CountryInfoState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onReloadClick() = loadData()

    private fun loadData() = viewModelScope.launch {
        try {
            _state.update { it.copy(loading = true, error = null) }
            val info = repository.getCountryInfoByCode(code)
            _state.update { it.copy(info = info) }
        } catch (e: Exception) {
            Log.e("COUNTRY_INFO_SCREEN", e.message ?: "")
            _state.update { it.copy(error = e) }
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application =
                    checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val savedStateHandle = createSavedStateHandle()
                CountryInfoViewModel(savedStateHandle, (application as App).repository)
            }
        }
    }
}