package com.plcoding.bluetoothchat.presentation.components.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.ui.theme.Shapes

@Composable
fun CustomResponsiveButton(deviceName: String?, buttonTitle: String, buttonColorId: Int, buttonEvent: () -> Unit) {
    Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),){
        Text(
            text = deviceName ?: "(No name)",
            modifier = Modifier.padding(16.dp)
        )
        Button(
            shape = Shapes.large,
            onClick = buttonEvent,
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = buttonColorId)),
        ) {
            Text(text = buttonTitle, fontSize = 14.sp, color = colorResource(id = R.color.white))
        }
    }
}