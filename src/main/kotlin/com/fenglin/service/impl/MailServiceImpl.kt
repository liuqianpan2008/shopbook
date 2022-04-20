package com.fenglin.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import javax.mail.MessagingException

@Service
class MailServiceImpl(@Autowired internal val mailSender: JavaMailSender) {
    fun SeedMail(addressee: String?,title:String="书城项目", content: String?): Boolean {
        val mimeMessage = mailSender.createMimeMessage()
        return try {
            val helper = MimeMessageHelper(mimeMessage, true)
            //发件人
            helper.setFrom("liugan@sc3248.wecom.work")
            //收件人
            helper.setTo(addressee!!)
            //标题
            helper.setSubject(title)
            //文本
            helper.setText(content!!)
            mailSender.send(mimeMessage)
            true
        } catch (e: MessagingException) {
            e.printStackTrace()
            false
        }
    }
    fun SeedAdminMail(addressee: String?, content: String?): Boolean {
        val mimeMessage = mailSender.createMimeMessage()
        return try {
            val helper = MimeMessageHelper(mimeMessage, true)
            //发件人
            helper.setFrom("liugan@sc3248.wecom.work")
            //收件人
            helper.setTo("liugan@sc3248.wecom.work")
            //标题
            helper.setSubject(addressee!!)
            //文本
            helper.setText(content!!)
            mailSender.send(mimeMessage)
            true
        } catch (e: MessagingException) {
            e.printStackTrace()
            false
        }
    }
}