package com.alitvinova.countriesapp.network.entity.bloc

import com.alitvinova.countriesapp.network.entity.ImageLinks

data class CountryBlocListItemModel(
    var name: String? = null,
    var topLevelDomain: ArrayList<String> = arrayListOf(),
    var alpha2Code: String? = null,
    var alpha3Code: String? = null,
    var callingCodes: ArrayList<String> = arrayListOf(),
    var capital: String? = null,
    var altSpellings: ArrayList<String> = arrayListOf(),
    var subregion: String? = null,
    var region: String? = null,
    var population: Int? = null,
    var latlng: ArrayList<Double> = arrayListOf(),
    var demonym: String? = null,
    var area: Double? = null,
    var timezones: ArrayList<String> = arrayListOf(),
    var nativeName: String? = null,
    var numericCode: String? = null,
    var flags: ImageLinks? = null,
    var currencies: ArrayList<Currencies> = arrayListOf(),
    var languages: ArrayList<Languages> = arrayListOf(),
    var flag: String? = null,
    var regionalBlocs: ArrayList<RegionalBlocs> = arrayListOf(),
    var independent: Boolean? = null
)