package com.alitvinova.countriesapp.network.entity

data class CountryListItemModel(
    val name: Name,
    val flags: Flags,
)

data class Name(
    val common: String,
    val official: String,
)

data class Flags(
    val png: String,
    val svg: String,
)
