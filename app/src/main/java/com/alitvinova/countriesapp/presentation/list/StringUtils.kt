package com.alitvinova.countriesapp.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.alitvinova.countriesapp.R
import com.alitvinova.countriesapp.domain.entity.RegionalBloc

@ReadOnlyComposable
@Composable
fun RegionalBloc.toStringRes() = stringResource(
    when (this) {
        RegionalBloc.EU -> R.string.country_list_eu
        RegionalBloc.EFTA -> R.string.country_list_efta
        RegionalBloc.CARICOM -> R.string.country_list_caricom
        RegionalBloc.PA -> R.string.country_list_pa
        RegionalBloc.AU -> R.string.country_list_au
        RegionalBloc.USAN -> R.string.country_list_usan
        RegionalBloc.EEU -> R.string.country_list_eeu
        RegionalBloc.AL -> R.string.country_list_al
        RegionalBloc.ASEAN -> R.string.country_list_asean
        RegionalBloc.CAIS -> R.string.country_list_cais
        RegionalBloc.CEFTA -> R.string.country_list_cefta
        RegionalBloc.NAFTA -> R.string.country_list_nafta
        RegionalBloc.SAARC -> R.string.country_list_saarc
    }
)
