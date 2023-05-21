package com.plcoding.bluetoothchat.presentation.components.common_components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.bluetoothchat.R

@Composable
fun CustomLargeText(title: String){
    // for titles
    Text(
        text = title,
        color = colorResource(id = R.color.black),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun CustomMediumText(title: String) {
    // for medium texts
    Text(
        text = title,
        color = colorResource(id = R.color.white),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )
}