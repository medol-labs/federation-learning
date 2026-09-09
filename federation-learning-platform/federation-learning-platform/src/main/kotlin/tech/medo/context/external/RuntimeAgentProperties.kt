package tech.medo.external

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "external.runtime-agent")
data class RuntimeAgentProperties(
    var endpoint: String = ""
)
