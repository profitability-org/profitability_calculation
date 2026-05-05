package com.induce.cbrservice

import com.induce.cbrservice.service.CbrService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootApplication
@EnableScheduling
class CbrServiceApplication

fun main(args: Array<String>) {
    runApplication<CbrServiceApplication>(*args)
}
