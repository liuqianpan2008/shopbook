package com.fenglin.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.fenglin.domain.notice
import org.apache.ibatis.annotations.Mapper

@Mapper
interface noticeDao: BaseMapper<notice> {
}