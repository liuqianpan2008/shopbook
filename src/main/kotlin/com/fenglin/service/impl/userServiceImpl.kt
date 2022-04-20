package com.fenglin.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.fenglin.dao.userDao
import com.fenglin.domain.User
import com.fenglin.service.IuserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class userServiceImpl(
    @Autowired
    internal val userDao: userDao
) : ServiceImpl<userDao, User>(), IuserService {
//    登录成功返回id，失败返回-1
    override fun login(userName: String, userpasswd: String): Int {
        val qwUser = QueryWrapper<User>()
        qwUser.eq("user_name", userName);
        val user1=userDao.selectOne(qwUser)
        return if (user1?.userName == userName && user1?.userPassword == userpasswd) user1?.id?:0 else -1
    }
}
