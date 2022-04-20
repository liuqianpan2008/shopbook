package com.fenglin.Config

import cn.dev33.satoken.exception.DisableLoginException
import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.NotPermissionException
import cn.dev33.satoken.exception.NotRoleException
import com.fenglin.Utils.Res
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


    @ControllerAdvice
    class GlobalException {
        // 全局异常拦截（拦截项目中的所有异常）
        @ResponseBody
        @ExceptionHandler
        @Throws(Exception::class)
        fun handlerException(e: Exception, request: HttpServletRequest?, response: HttpServletResponse?): Res? {
            // 打印堆栈，以供调试
            println("全局异常---------------")
            e.printStackTrace()
            // 不同异常返回不同状态码
            var aj: Res? = null
            aj = if (e is NotLoginException) {    // 如果是未登录异常
                Res(false, msg = "无效token，请重新登录")
            } else if (e is NotRoleException) {        // 如果是角色异常
                Res(false,msg = "无此角色："+ e.role)
            } else if (e is NotPermissionException) {    // 如果是权限异常
                Res(false,msg = "无此权限："+ e.code)

            } else if (e is DisableLoginException) {    // 如果是被封禁异常
                Res(false,msg = "账号被封禁："+ e.disableTime + "秒后解封")
            } else {    // 普通异常, 输出：500 + 异常信息
                e.message?.let { Res(false,msg = it) }
            }
            // 返回给前端
            return aj
        }
    }
