package com.fenglin.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.fenglin.dao.noticeDao
import com.fenglin.domain.notice
import com.fenglin.service.InoticeServuce
import org.springframework.stereotype.Service

@Service
class NoticeServicelmpl: ServiceImpl<noticeDao, notice>(), InoticeServuce {

}