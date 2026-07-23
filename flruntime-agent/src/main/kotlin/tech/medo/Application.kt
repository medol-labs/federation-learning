package tech.medo

import org.axonframework.extension.springboot.autoconfig.JpaEventStoreAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(exclude = [JpaEventStoreAutoConfiguration::class])
@ConfigurationPropertiesScan
@EnableFeignClients
@EnableScheduling
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
class FLRuntimeAgentApplication

fun main(args: Array<String>) {
    runApplication<FLRuntimeAgentApplication>(*args)
}
