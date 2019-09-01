package com.example.advance.callblocker.permissions

import android.Manifest.permission
import android.annotation.TargetApi
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.telecom.TelecomManager
import android.widget.Toast
import androidx.annotation.RequiresApi

// Calendar group.
const val READ_CALENDAR = permission.READ_CALENDAR
const val WRITE_CALENDAR = permission.WRITE_CALENDAR

// Camera group.
const val CAMERA = permission.CAMERA

// Contacts group.
const val READ_CONTACTS = permission.READ_CONTACTS
const val WRITE_CONTACTS = permission.WRITE_CONTACTS

// Location group.
const val ACCESS_FINE_LOCATION = permission.ACCESS_FINE_LOCATION
const val ACCESS_COARSE_LOCATION = permission.ACCESS_COARSE_LOCATION

// Microphone group.
const val RECORD_AUDIO = permission.RECORD_AUDIO

// Phone group.
const val READ_PHONE_STATE = permission.READ_PHONE_STATE
const val CALL_PHONE = permission.CALL_PHONE
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
const val READ_CALL_LOG = permission.READ_CALL_LOG
const val WRITE_CALL_LOG = permission.WRITE_CALL_LOG
const val ADD_VOICEMAIL = permission.ADD_VOICEMAIL
const val USE_SIP = permission.USE_SIP
const val PROCESS_OUTGOING_CALLS = permission.PROCESS_OUTGOING_CALLS

// Sensors group.
@RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
const val BODY_SENSORS = permission.BODY_SENSORS

// SMS group.
const val SEND_SMS = permission.SEND_SMS
const val RECEIVE_SMS = permission.RECEIVE_SMS
const val READ_SMS = permission.READ_SMS
const val RECEIVE_WAP_PUSH = permission.RECEIVE_WAP_PUSH
const val RECEIVE_MMS = permission.RECEIVE_MMS
const val READ_CELL_BROADCASTS = "android.permission.READ_CELL_BROADCASTS"


//Storage Group
const val READ_STORAGE = permission.READ_EXTERNAL_STORAGE
const val WRITE_STORAGE = permission.WRITE_EXTERNAL_STORAGE


// Bookmarks group.
const val READ_HISTORY_BOOKMARKS = "com.android.browser2.permission.READ_HISTORY_BOOKMARKS"
const val WRITE_HISTORY_BOOKMARKS = "com.android.browser2.permission.WRITE_HISTORY_BOOKMARKS"


val STORAGE_PERMISSION = arrayOf(READ_STORAGE, WRITE_STORAGE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
const val STORAGE_PERMISSION_REQUEST_CODE = 100

object Permissions {


    fun hasGranted(grantResult: Int) = grantResult == PackageManager.PERMISSION_GRANTED

    fun hasGranted(grantResult: IntArray): Boolean {
        grantResult.forEach {
            if (!hasGranted(it)) return false
        }
        return true
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun permissionHasGranted(context: Context, permission: String) =
        if (isM()) hasGranted(context.checkSelfPermission(permission)) else true;

    fun isM(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    fun hasSelfPermission(context: Context, permission: String) = permissionHasGranted(context, permission)

    fun hasSelfPermission(context: Context, permissions: Array<String>): Boolean {
        permissions.forEach {
            if (!permissionHasGranted(context, it))
                return false
        }
        return true
    }

    fun requestAllPermissions(activity: Activity, permissions: Array<String>, requestCode: Int) {
        if (isM()) {
            activity.requestPermissions(permissions, requestCode)
        }
    }

    fun checkSetDefaultDialerResult(activity: Activity, resultCode: Int) {
        val message = when (resultCode) {
            RESULT_OK -> "User accepted request to become default dialer"
            RESULT_CANCELED -> "User declined request to become default dialer"
            else -> "Unexpected result code $resultCode"
        }

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun checkDefaultDailerApp(activity: Activity): Boolean {
        if (isM()) {
            val telecomManager = activity.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            val isAlreadyDefaultDialer = activity.packageName == telecomManager.defaultDialerPackage
            if (isAlreadyDefaultDialer) return true
        }
        return false
    }

    fun checkDefaultDialer(activity: Activity, requestCode: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
            .putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, activity.packageName)
        activity.startActivityForResult(intent, requestCode)
    }

    fun canDrawOverlay(activity: Context): Boolean {
        if (isM()) {
            return Settings.canDrawOverlays(activity)
        }
        return true
    }

    fun requestOverlayPermission(activity: Activity, requestCode: Int) {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        intent.data = Uri.parse("package:${activity.packageName}")
        activity.startActivityForResult(intent, requestCode)

    }


    interface OnPermissionGrantedCallback {
        fun onPermissionRequestResult(requestCode: Int, isGranted: Boolean)
    }

    interface OnOverlayPermissionCallback{
        fun onOverplayPermissionResult(isGranted : Boolean)
    }


}