package com.fenglin

import com.fenglin.service.impl.MailServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate


@SpringBootTest
class BookShopDemoApplicationTests(
    @Autowired
    internal val MailServiceImpl: MailServiceImpl,
    @Autowired
    internal val RedisTemplate: StringRedisTemplate,
) {

    @Test
    fun contextLoads() {

    }

}
