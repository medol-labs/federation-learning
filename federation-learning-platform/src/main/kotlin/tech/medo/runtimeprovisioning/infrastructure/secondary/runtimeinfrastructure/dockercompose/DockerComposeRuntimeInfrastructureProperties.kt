package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.dockercompose

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "platform.runtime.docker-compose")
class DockerComposeRuntimeInfrastructureProperties {
    var enabled: Boolean = true
    var composeFile: String = "../docker-compose.yml"
    var projectDirectory: String? = null
    var agentServiceName: String = "federation-learning-runtime-agent"
    var agentVersion: String = "docker-compose"
    var commandTimeout: Duration = Duration.ofSeconds(60)
    var supportedDeploymentTargetTypes: List<String> = listOf("DOCKER_COMPOSE_HOST")
    var supportedEnvironmentTypes: List<String> = listOf("DOCKER_COMPOSE")
}
