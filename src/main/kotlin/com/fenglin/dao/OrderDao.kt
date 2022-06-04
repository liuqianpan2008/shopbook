package com.fenglin.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper

import com.fenglin.domain.Bookorder
import org.apache.ibatis.annotations.Mapper

@Mapper
interface OrderDao: BaseMapper<Bookorder> {
}