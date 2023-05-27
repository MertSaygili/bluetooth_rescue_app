package com.plcoding.bluetoothchat.util.toasts

import android.content.Context
import com.shashank.sony.fancytoastlib.FancyToast

class CustomToasts () {
    fun customToast(context: Context, text: String, type: Int) {
        FancyToast.makeText(context, text, FancyToast.LENGTH_LONG, type, false).show()
    }
}