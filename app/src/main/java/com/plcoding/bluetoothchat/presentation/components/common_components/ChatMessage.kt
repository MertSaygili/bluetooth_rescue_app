package com.plcoding.bluetoothchat.presentation.components.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothMessage
import com.plcoding.bluetoothchat.ui.theme.BluetoothChatTheme
import com.plcoding.bluetoothchat.ui.theme.DarkWhite
import com.plcoding.bluetoothchat.ui.theme.OpenBlue
import com.plcoding.bluetoothchat.util.time.Time


@Composable
fun ChatMessage(message: BluetoothMessage, modifier: Modifier = Modifier ){
    // chat message component
    Column(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = if (message.isFromLocalUser) 15.dp else 0.dp,
                    topEnd = 15.dp,
                    bottomStart = 15.dp,
                    bottomEnd = if (message.isFromLocalUser) 0.dp else 15.dp
                )
            )
            .background(if (message.isFromLocalUser) OpenBlue else DarkWhite)
            .padding(16.dp)
    ) {
        // sender name
        Text(
            text = message.senderName,
            fontSize = 10.sp,
            color = colorResource(id = R.color.black),
        )
        // content
        Text(
            text = message.message,
            fontSize = 14.sp,
            color = colorResource(id = R.color.black),
            modifier = Modifier.widthIn(max = 250.dp)
        )
        // date
        Row (
            modifier =Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ){
            Text(
                text = message.sendDate,
                fontSize = 8.sp,
                color = Color.Black,
                textAlign = TextAlign.End
            )
        }

    }
}

@Preview
@Composable
fun preview() {
    BluetoothChatTheme {
        ChatMessage(
            message = BluetoothMessage(
                message = "Hello World!",
                senderName = "Pixel 6",
                isFromLocalUser = false,
                sendDate = Time().getCurrentTimeForMessage()
            )
        )
    }
}
