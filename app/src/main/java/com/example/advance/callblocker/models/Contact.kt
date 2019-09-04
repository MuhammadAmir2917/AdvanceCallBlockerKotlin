package com.example.advance.callblocker.models

import java.io.Serializable

data class Contact(
    var id : Long = -1,
    var path : String? = null,
    var name : String = "" ,
    var phone : String = "",
    var email : String ="",
    var type : String ="",
    var fav : Int = 0,
    var key : String = "") : Serializable{
    var sectionType : Int =0
}