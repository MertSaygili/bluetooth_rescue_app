package com.plcoding.bluetoothchat.presentation.components.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.plcoding.bluetoothchat.constants.Strings

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomAppbar
import com.plcoding.bluetoothchat.ui.theme.Shapes

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { CustomAppbar(title = stringResource(id = R.string.appbar_title)) }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomButton(text = stringResource(id = R.string.send_SOS), colorId = R.color.error) {}
                CustomButton(text = stringResource(id = R.string.connect_with_devices), colorId = R.color.success) {
                    navController.navigate(Strings.main_route_name)
                }
            }
        }
    }
}

@Composable
fun CustomButton(text: String, colorId: Int, event: () -> Unit, ) {
    val buttonModifier = Modifier
        .padding(20.dp)
        .height(50.dp)
        .fillMaxWidth()

    Button(
        modifier = buttonModifier,
        shape = Shapes.large,
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = colorId)),
        onClick = event
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.white))
    }
}


