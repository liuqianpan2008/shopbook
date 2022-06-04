package com.fenglin.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.fenglin.dao.ShopDao
import com.fenglin.domain.shop
import com.fenglin.service.IshopService
import org.springframework.stereotype.Service

@Service
class ShopServicelmpl: ServiceImpl<ShopDao, shop>(), IshopService {

}