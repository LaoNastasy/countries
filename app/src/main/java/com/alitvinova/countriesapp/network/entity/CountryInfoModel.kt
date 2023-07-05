package com.alitvinova.countriesapp.network.entity

data class CountryInfoModel(
    val name: Name,
    val flags: ImageLinks?,
    val maps: Maps?,
    val population: Long?,
    val coatOfArms: ImageLinks?,
)
