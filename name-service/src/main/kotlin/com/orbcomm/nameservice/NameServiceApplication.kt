package com.orbcomm.nameservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@ConfigurationProperties
class NameProperties {
    lateinit var name : String
}

@RestController
class MainController(private val properties : NameProperties) {
    @GetMapping("/")
    fun index() : String {
        return properties.name
    }
}

@SpringBootApplication
@EnableConfigurationProperties(NameProperties::class)
class NameServiceApplication

fun main(args: Array<String>) {
    runApplication<NameServiceApplication>(*args)
}
