package com.fenglin.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class helloWorld {
    @GetMapping("/helloWorld")
    fun home() : String{
        return "你好,世界!"
    }
}