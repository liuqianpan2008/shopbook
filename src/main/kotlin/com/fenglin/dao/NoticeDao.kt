package com.fenglin.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.fenglin.domain.Notice
import org.apache.ibatis.annotations.Mapper

@Mapper
interface NoticeDao: BaseMapper<Notice> {
}