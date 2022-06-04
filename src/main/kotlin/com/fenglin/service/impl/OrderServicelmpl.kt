package com.fenglin.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.fenglin.dao.OrderDao
import com.fenglin.domain.Bookorder
import com.fenglin.service.IorderService
import org.springframework.stereotype.Service

@Service
class OrderServicelmpl: ServiceImpl<OrderDao, Bookorder>(), IorderService {

}