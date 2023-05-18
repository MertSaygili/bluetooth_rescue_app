package com.plcoding.bluetoothchat.presentation.components.common_components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.ui.theme.Shapes

@Composable
fun CustomHorizontalButton(text: String, colorId: Int, event: () -> Unit, ) {
    Button(
        modifier = Modifier.padding(20.dp).height(50.dp).fillMaxWidth(),
        shape = Shapes.large,
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = colorId)),
        onClick = event
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.white))
    }
}

@Composable
fun CustomSmallButton(textId: Int, colorId: Int, event: () -> Unit) {
    Button(
        modifier = Modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = colorId)),
        shape = Shapes.small,
        contentPadding = PaddingValues(0.dp),
        onClick = event,
        ) {
        CustomMediumText(title = stringResource(id = textId))
    }
}


