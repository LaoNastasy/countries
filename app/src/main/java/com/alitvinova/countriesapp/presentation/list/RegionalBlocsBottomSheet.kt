package com.alitvinova.countriesapp.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alitvinova.countriesapp.R
import com.alitvinova.countriesapp.ui.theme.Purple40
import com.alitvinova.countriesapp.ui.theme.Purple80
import com.alitvinova.countriesapp.ui.theme.PurpleGrey40
import com.alitvinova.countriesapp.ui.theme.Typography
import com.alitvinova.countriesapp.ui.theme.White
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun RegionalBlocsBottomSheet(viewModel: CountriesListViewModel) {
    val state = viewModel.state.collectAsState().value
    val blocks = RegionalBloc.values().toList().map { Pair(it, state.filter == it) }
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.country_list_regional_bloc),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
            Modifier.padding(horizontal = 16.dp), mainAxisSpacing = 4.dp, crossAxisSpacing = 8.dp
        ) {
            blocks.forEach { (bloc, selected) ->

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (selected) {
                        Purple40
                    } else {
                        Purple80
                    },
                ) {
                    Text(
                        text = bloc.toStringRes(),
                        modifier = Modifier
                            .clickable(onClick = { viewModel.onBlocClick(bloc) })
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        style = Typography.labelMedium,
                        color = if (selected) White else Purple40,
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = viewModel::onFiltersChosen,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PurpleGrey40
            )
        ) {
            Text(
                text = stringResource(R.string.country_list_filter_apply),
                style = Typography.titleMedium,
            )
        }
    }
}
