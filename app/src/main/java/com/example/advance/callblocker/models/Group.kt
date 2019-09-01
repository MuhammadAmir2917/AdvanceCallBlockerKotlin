package com.example.advance.callblocker.models

data class Group(
    var id : Long,
    var name : String,
    var count : Int
){
    var isShow : Boolean =false
}