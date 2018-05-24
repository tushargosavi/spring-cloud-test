package com.orbcomm.helloservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Service
class GreetingData {
    val greeting : String = "Hello"
    val greetings : Map<String, String> = hashMapOf("EN" to "Hello", "ES" to "Holla", "DE" to "Hallo")
}

@RestController
class GreetingController(private val properties: GreetingData) {

    @RequestMapping("/{languageCode}")
    fun getGreeting(@PathVariable languageCode : String) : String {
        return properties.greetings.getOrDefault(languageCode.toUpperCase(), properties.greeting)
    }

    @RequestMapping("/")
    fun getGreeting() : String {
        return properties.greeting
    }
}

@SpringBootApplication
class HelloServiceApplication

fun main(args: Array<String>) {
    runApplication<HelloServiceApplication>(*args)
}
