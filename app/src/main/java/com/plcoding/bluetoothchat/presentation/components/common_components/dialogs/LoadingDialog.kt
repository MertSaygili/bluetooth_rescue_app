package com.plcoding.bluetoothchat.presentation.components.common_components.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomMediumText


@Composable
fun LoadingDialog (titleID: Int, close: () -> Unit) {
    Dialog(
        onDismissRequest = {
            close()
        }, properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        content = {
            Surface(
                color = Color.Transparent,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    CircularProgressIndicator(color = Color.White)
                    CustomMediumText(title = stringResource(id = titleID))
                }

            }
        }
    )
}
