package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.submitagentlocalmodelupdate

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("runtime-agent.local-model-update-submission")
data class LocalModelUpdateSubmissionProperties(
    val enabled: Boolean = true,
    val platformUrl: String = "http://localhost:8081",
    val supportUrl: String = "http://localhost:8080",
    val internalToken: String = "local-dev-internal-token"
)
