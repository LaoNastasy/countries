package com.alitvinova.countriesapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alitvinova.countriesapp.R
import com.alitvinova.countriesapp.network.DomainException
import com.alitvinova.countriesapp.ui.theme.Typography

@Composable
fun ErrorInfo(
    error: DomainException,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Spacer(Modifier.height(16.dp))
    Text(
        text = when (error) {
            DomainException.NoConnectionException -> stringResource(R.string.error_text_no_connection)
            is DomainException.TextException -> error.text
            DomainException.TimeOutException -> stringResource(R.string.error_text_timeout)
            DomainException.Unknown -> stringResource(R.string.error_text_unknown)
        },
        style = Typography.titleMedium
    )
    Button(
        onClick = onRetryClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(R.string.error_action),
            style = Typography.titleMedium
        )
    }
}
