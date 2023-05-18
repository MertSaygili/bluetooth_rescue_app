package com.plcoding.bluetoothchat.presentation.components.common_components

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.plcoding.bluetoothchat.R

@Composable
fun CustomAppbar(context: Context?, title: String, needAction: Boolean) {
    TopAppBar(
        title  = { Text(text = title, color = colorResource(id = R.color.white))},
        backgroundColor = colorResource(id = R.color.success),
        elevation = 8.dp,
        actions = {
            if (needAction) {
                IconButton(
                    onClick = { context?.startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS)) }
                ) {
                    Icon(Icons.Filled.Settings, "Settings", tint = colorResource(id = R.color.white))
                }
            }
        }
    )
}