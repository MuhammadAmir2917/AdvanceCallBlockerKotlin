package com.example.advance.callblocker.callbacks

import com.example.advance.callblocker.models.Contact

interface OnContactFavoriteChangeListener {
    fun onContactFavoriteChange(contact : Contact)
}