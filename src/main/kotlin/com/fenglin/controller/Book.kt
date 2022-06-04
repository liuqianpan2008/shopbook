package com.fenglin.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.fenglin.utils.Res
import com.fenglin.dao.BookDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/book")
class Book(
    @Autowired
    internal val bookDao: BookDao
) {
    @CrossOrigin("*")
    @PostMapping("/allbook")
    fun allbook(@RequestBody data:Map<String,String>): Res {
        val qwbook = QueryWrapper<com.fenglin.domain.Book>()
        data["tag"]?.let {
            if (it != ""){
                qwbook.eq("tag", it)
            }
        }
        data["search"]?.let {
            if (it != "") {
                qwbook.like("name", it).or().like("sketch", it)
            }
        }
        val iPage: IPage<com.fenglin.domain.Book> = Page(data["current"]?.toLong()?:0, data["size"]?.toLong()?:0)
        val notices=bookDao.selectPage(iPage,qwbook)
        notices?.let {
            return Res(date = it, msg = "获取成功！")
        }
        return Res(flag = false, msg = "无数据！")
    }
//   公告

    @CrossOrigin("*")
    @PostMapping("/bookId")
    fun bookId(@RequestBody data:Map<String,String>): Res {
        return Res(date = bookDao.selectById(data["id"]), msg = "获取成功！")
    }
}