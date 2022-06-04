package com.fenglin.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.fenglin.dao.NoticeDao
import com.fenglin.domain.Notice
import com.fenglin.service.InoticeServuce
import org.springframework.stereotype.Service

@Service
class NoticeServicelmpl: ServiceImpl<NoticeDao, Notice>(), InoticeServuce {

}