package com.example.advance.callblocker.dataloader

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.example.advance.callblocker.R
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.item_contact.view.*

object ProfileLoader{
    fun loadProfileImage(context: Context,view : RoundedImageView , path : String?){
        Glide.with(context).load(path).placeholder(R.drawable.user_photo).into(view)
    }
}