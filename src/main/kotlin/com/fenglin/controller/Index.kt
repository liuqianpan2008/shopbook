package com.fenglin.controller

import com.fenglin.Utils.Res
import com.fenglin.dao.noticeDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin("*")
class Index(
    @Autowired
    internal val noticeDao: noticeDao,
    ) {

    @CrossOrigin("*")
    @GetMapping("/notices")
    fun notice():Res{
        val notices=noticeDao.selectList(null)
        notices.forEach {
            it.cod=it.time.time
        }
        return Res(date = notices, msg = "获取成功！")
    }
}