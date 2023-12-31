package com.study.library.account

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class AccountApplication

fun main(args: Array<String>) {
    runApplication<AccountApplication>(*args)
}
