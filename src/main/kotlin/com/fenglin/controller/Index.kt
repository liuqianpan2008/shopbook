package com.fenglin.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.fenglin.utils.Res
import com.fenglin.dao.NoticeDao
import com.fenglin.domain.Notice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin("*")
class Index(
    @Autowired
    internal val noticeDao: NoticeDao,
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

    @CrossOrigin("*")
    @PostMapping("/allnotices")
    fun allnotices(@RequestBody data:Map<String,String>): Res {
        val qwnotice = QueryWrapper<Notice>()
        data["search"]?.let {
            if (it != "") {
                qwnotice.like("title", it).or().like("id", it)
            }
        }
        val iPage: IPage<Notice> = Page(data["current"]?.toLong()?:0, data["size"]?.toLong()?:0)
        val notices=noticeDao.selectPage(iPage,qwnotice)
        notices?.let {
            return Res(date = it, msg = "获取成功！")
        }
        return Res(flag = false, msg = "无数据！")
    }
}