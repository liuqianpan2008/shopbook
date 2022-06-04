package com.fenglin.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.fenglin.domain.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserDao : BaseMapper<User>{

}