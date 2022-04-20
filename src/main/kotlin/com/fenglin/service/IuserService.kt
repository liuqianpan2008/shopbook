package com.fenglin.service

import com.baomidou.mybatisplus.extension.service.IService
import com.fenglin.domain.User

interface  IuserService : IService<User> {
    fun login( userName : String, userpasswd : String ):Int
}