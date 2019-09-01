package com.example.advance.callblocker.callbacks

import com.example.advance.callblocker.models.Contact

interface OnContactOptionClickListener{
    fun onCallItemClick(contact : Contact)
    fun onMessageItemClick(contact : Contact)
    fun onEditItemClick(contact : Contact)
    fun onDeleteItemClick(contact : Contact)
}