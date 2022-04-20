package com.fenglin.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.fenglin.domain.shop
import org.apache.ibatis.annotations.Mapper

@Mapper
interface shopDao : BaseMapper<shop> {
}