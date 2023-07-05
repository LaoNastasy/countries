package com.alitvinova.countriesapp.network.entity

data class CountryInfoModel(
    val name: Name,
    val flags: ImageLinks?,
    val maps: Maps?,
    val population: Long?,
    val coatOfArms: ImageLinks?,
    val capital: List<String>?,
    val languages: Map<String, String>?,
    val continents: List<String>?,
)
