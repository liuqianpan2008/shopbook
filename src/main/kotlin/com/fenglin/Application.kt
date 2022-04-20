package com.fenglin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookShopDemoApplication

fun main(args: Array<String>) {
    runApplication<BookShopDemoApplication>(*args)
}
