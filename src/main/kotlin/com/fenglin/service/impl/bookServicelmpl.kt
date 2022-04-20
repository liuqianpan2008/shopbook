package com.fenglin.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.fenglin.dao.bookDao
import com.fenglin.domain.Book
import com.fenglin.service.IbookService
import org.springframework.stereotype.Service


@Service
class bookServicelmpl: ServiceImpl<bookDao, Book>(), IbookService {

}