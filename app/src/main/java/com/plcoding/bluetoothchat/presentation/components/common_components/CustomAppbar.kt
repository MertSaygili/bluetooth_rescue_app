package com.plcoding.bluetoothchat.presentation.components.common_components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.plcoding.bluetoothchat.R

@Composable
fun CustomAppbar(title: String) {
    TopAppBar(
        title  = { Text(text = title, color = colorResource(id = R.color.white))},
        backgroundColor = colorResource(id = R.color.success),
        elevation = 8.dp
    )
}