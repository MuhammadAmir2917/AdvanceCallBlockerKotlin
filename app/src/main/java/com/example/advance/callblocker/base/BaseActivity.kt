package com.example.advance.callblocker.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.advance.callblocker.R
import com.example.advance.callblocker.permissions.Permissions
import com.example.advance.callblocker.utils.OVERLAY_PERMISSION_REQUEST_CODE

abstract class BaseActivity : AppCompatActivity(){


    private var permissionCallback : Permissions.OnPermissionGrantedCallback? =null
    private var requestCode = -1
    private var overlayPermissionCallback : Permissions.OnOverlayPermissionCallback? = null


    @get:LayoutRes
    protected abstract val layoutResources : Int
    protected abstract fun onActivityReady(savedInstanceState : Bundle?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResources)


        onActivityReady(savedInstanceState)
    }

    fun addFragment(savedInstanceState: Bundle? ,container : Int , fragment: Fragment){
         savedInstanceState ?: supportFragmentManager.inTransaction {
            setCustomAnimations(android.R.animator.fade_in , android.R.animator.fade_out)
             add(container , fragment ,fragment.tag)
        }
    }

    fun replaceFragment(savedInstanceState: Bundle? ,container : Int , fragment: Fragment ){
        savedInstanceState ?: supportFragmentManager.inTransaction {
            setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
            replace(container , fragment , fragment.tag)
            addToBackStack(fragment.tag)
        }
    }

    fun getColorRes(@ColorRes id : Int) = ContextCompat.getColor(this , id)

    fun changeStatusBarColor(color : Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.statusBarColor = color
        }
    }

    fun isM() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M


    fun permissionRequest(permissions : Array<String> , requestCode : Int , callback : Permissions.OnPermissionGrantedCallback?){
        this.permissionCallback = callback
        this.requestCode = requestCode
        if(Permissions.hasSelfPermission(this , permissions)){
            this.permissionCallback?.onPermissionRequestResult(requestCode , true)
            this.permissionCallback = null
        }else{
            Permissions.requestAllPermissions(this , permissions , requestCode)
        }
    }

    fun hasOverlayPermission(callback : Permissions.OnOverlayPermissionCallback?){
        this.overlayPermissionCallback = callback
        if(isM()) {
            if (Permissions.canDrawOverlay(this)) {
                overlayPermissionCallback?.onOverplayPermissionResult(true)
                overlayPermissionCallback = null
            }else{
                Permissions.requestOverlayPermission(this , OVERLAY_PERMISSION_REQUEST_CODE)
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == OVERLAY_PERMISSION_REQUEST_CODE){
            if(isM()){
                if(Permissions.canDrawOverlay(this)){
                    overlayPermissionCallback?.onOverplayPermissionResult(true)
                }else{
                    overlayPermissionCallback?.onOverplayPermissionResult(false)
                }
            }
        }



        overlayPermissionCallback= null
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == this.requestCode){
            if(grantResults.isNotEmpty() && Permissions.hasGranted(grantResults)){
                this.permissionCallback?.onPermissionRequestResult(requestCode , true)
            }else{
                this.permissionCallback?.onPermissionRequestResult(requestCode , false)
            }

            this.permissionCallback = null

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}