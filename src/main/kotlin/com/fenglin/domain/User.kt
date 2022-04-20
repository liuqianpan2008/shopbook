package com.fenglin.domain

data class User(
    val id : Int?, //用户ID
    val userName : String, //用户账号
    var userPassword : String, //用户密码
    val mail : String,
    val sex : String, //性别
    val phone : String?, //电话
    val introduce : String?, //个人介绍
    var mailcod : String?
)
