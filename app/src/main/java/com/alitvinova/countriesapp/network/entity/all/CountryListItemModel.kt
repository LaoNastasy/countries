package com.alitvinova.countriesapp.network.entity.all

import com.alitvinova.countriesapp.network.entity.ImageLinks
import com.alitvinova.countriesapp.network.entity.Maps


data class CountryListItemModel(
    var name: Name? = Name(),
    var tld: ArrayList<String> = arrayListOf(),
    var cca2: String? = null,
    var ccn3: String? = null,
    var cca3: String? = null,
    var cioc: String? = null,
    var independent: Boolean? = null,
    var status: String? = null,
    var unMember: Boolean? = null,
    var currencies: Currencies? = Currencies(),
    var capital: ArrayList<String> = arrayListOf(),
    var altSpellings: ArrayList<String> = arrayListOf(),
    var region: String? = null,
    var subregion: String? = null,
    var latlng: ArrayList<Double> = arrayListOf(),
    var landlocked: Boolean? = null,
    var borders: ArrayList<String> = arrayListOf(),
    var area: Double? = null,
    var flag: String? = null,
    var maps: Maps? = Maps(),
    var population: Int? = null,
    var fifa: String? = null,
    var car: Car? = Car(),
    var timezones: ArrayList<String> = arrayListOf(),
    var continents: ArrayList<String> = arrayListOf(),
    var flags: ImageLinks? = null,
    var coatOfArms: ImageLinks? = null,
    var startOfWeek: String? = null,
    var capitalInfo: CapitalInfo? = CapitalInfo(),
    var postalCode: PostalCode? = PostalCode()
)