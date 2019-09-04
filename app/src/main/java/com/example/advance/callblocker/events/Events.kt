package com.example.advance.callblocker.events

import com.example.advance.callblocker.models.Contact

class Events {
    data class ContactFavoriteEvent(var contact : Contact)
}