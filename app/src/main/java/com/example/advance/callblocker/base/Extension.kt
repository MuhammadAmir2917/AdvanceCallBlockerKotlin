package com.example.advance.callblocker.base

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.advance.callblocker.activities.MainActivity
import com.example.advance.callblocker.BuildConfig
import com.google.android.material.snackbar.Snackbar

const val LOG_TAG = "CallBlockerLog"

fun FragmentManager.inTransaction(func : FragmentTransaction.()-> FragmentTransaction)=
    beginTransaction().func().commitAllowingStateLoss()

fun Context.log(message: String = "", @StringRes resId: Int = 0) {
    if (BuildConfig.DEBUG)
        if (message.isNotEmpty())
            Log.d(LOG_TAG, message)
        else
            Log.d(LOG_TAG, getString(resId))
}

fun Context.toast(message: String = "", @StringRes resId: Int = 0) {
    val m = if (message.isNotEmpty())
        message
    else getString(resId)
    Toast.makeText(this, m, Toast.LENGTH_SHORT).show()
}

fun Context.snackBar(view: View, message: String = "", @StringRes resId: Int = 0) {
    val m = if (message.isNotEmpty())
        message
    else getString(resId)
    Snackbar.make(view, m, Snackbar.LENGTH_LONG).show()
    // .setAction("Action", null).show()
}

val Fragment.baseActivity : BaseActivity get() = activity as BaseActivity
val Fragment.mainActivity : MainActivity get() = activity as MainActivity
val Fragment.appContext : Context get() = activity as Context
//val Context.conversationRepo : ConversationRepository get() = ConversationRepository(AppDatabase.invoke().conversationDAO())
