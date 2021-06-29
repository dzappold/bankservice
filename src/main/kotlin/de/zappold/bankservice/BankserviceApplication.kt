package de.zappold.bankservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankserviceApplication

fun main(args: Array<String>) {
    runApplication<BankserviceApplication>(*args)
}
