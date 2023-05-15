package com.plcoding.bluetoothchat.presentation.components.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.constants.Strings
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true){
        delay(3000L)
        navController.navigate(Strings.home_route_name){
            // after pop up, closes app
            popUpTo(0)
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.ic_logo), contentDescription = "logo", contentScale = ContentScale.Crop)
    }
}