package com.fenglin.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.fenglin.domain.Book
import org.apache.ibatis.annotations.Mapper

@Mapper
interface BookDao: BaseMapper<Book> {

}