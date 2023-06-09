package com.plcoding.bluetoothchat.presentation.components.common_components.dialogs

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomSmallButton

@Composable
fun CustomAlertDialog(confirmButton: () -> Unit) {
    // alert dialog
    // pop ups if user does not open location
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(id = R.string.warning), fontWeight = FontWeight.Bold) },
        text = { Text(stringResource(id = R.string.open_GPS)) },
        confirmButton = { CustomSmallButton(textId = R.string.ok, colorId = R.color.error) { confirmButton() } },
    )
}

