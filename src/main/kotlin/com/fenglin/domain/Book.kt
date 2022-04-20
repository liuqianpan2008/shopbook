package com.fenglin.domain

data class Book(
    val id:Int,
    val name:String,
    val img:String,
    val sketch:String,
    val info:String,
    val tag:String,
    val stock:Int,
    val price:Int
)