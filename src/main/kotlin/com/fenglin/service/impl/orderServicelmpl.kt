package com.fenglin.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.fenglin.dao.OrderDao
import com.fenglin.domain.bookorder
import com.fenglin.service.IorderService
import org.springframework.stereotype.Service

@Service
class orderServicelmpl: ServiceImpl<OrderDao, bookorder>(), IorderService {

}