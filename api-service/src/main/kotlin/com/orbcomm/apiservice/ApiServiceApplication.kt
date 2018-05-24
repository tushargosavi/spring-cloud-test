package com.orbcomm.apiservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.servlet.support.RequestContextUtils
import javax.servlet.http.HttpServletRequest

@Service
class GreetingService(private val rest : RestTemplate) {

    private val URL : String = "http://localhost:8082"

    fun getGreeting() : String? {
        return rest.getForObject(URL, String::class)
    }

    fun getGreeting(locale : String?) : String? {
        return rest.getForObject(StringBuilder().append(URL).append("/").append(locale).toString(), String::class)
    }
}

@Service
class NameService(private val rest : RestTemplate) {
    private val URL : String = "http://localhost:8081"

    fun getName() : String? {
        return rest.getForObject(URL, String::class)
    }
}

@RestController
class ApiController(private val nameService: NameService,
                    private val greetingService: GreetingService) {

    @RequestMapping
    fun index(request : HttpServletRequest) : String {
        val locale : String? = RequestContextUtils.getLocaleResolver(request)?.resolveLocale(request)?.toLanguageTag()
        val greeting : String = StringBuilder().append(greetingService.getGreeting(locale))
                .append(" ").append(nameService.getName()).toString()
        return greeting
    }
}

@SpringBootApplication
class ApiServiceApplication {
    @Configuration
    class MyConfig {
        @Bean
        fun rest() : RestTemplate {
            return RestTemplateBuilder().build()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ApiServiceApplication>(*args)
}
