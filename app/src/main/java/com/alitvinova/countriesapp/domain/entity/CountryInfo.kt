package com.alitvinova.countriesapp.domain.entity

data class CountryInfo(
    val name: String,
    val officialName: String,
    val googleMapLink: String?,
    val population: Long?,
    val flag: String?,
    val coatOfArms: String?,
    val capital: String?,
    val languages: Map<String, String>?,
    val continents: List<String>?,
)
