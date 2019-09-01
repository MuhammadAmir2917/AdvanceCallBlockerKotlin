package com.example.advance.callblocker.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object NavigationUtils {
    fun openEmailApp(context: Context , mail : String){
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, mail)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}