package com.fenglin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSenderImpl


@Configuration
class MailConfiguration {
    @Bean
    fun JavaMailSender(): JavaMailSenderImpl {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.exmail.qq.com"
        mailSender.username = "liugan@sc3248.wecom.work"
        mailSender.password = "CraqtDX77NDp7FXg"
        return mailSender
    }
}