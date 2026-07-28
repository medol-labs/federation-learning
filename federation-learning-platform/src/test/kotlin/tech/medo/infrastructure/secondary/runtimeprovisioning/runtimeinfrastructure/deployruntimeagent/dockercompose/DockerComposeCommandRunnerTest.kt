package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DockerComposeCommandRunnerTest {
    @Test
    fun `default command uses runtime agent compose file`() {
        val properties = DockerComposeRuntimeInfrastructureProperties()

        val command = dockerComposeCommand(properties, listOf("up", "-d", properties.agentServiceName))

        assertEquals(
            listOf(
                "docker",
                "compose",
                "-f",
                "../docker-compose-runtime-agent.yml",
                "up",
                "-d",
                "federation-learning-runtime-agent"
            ),
            command
        )
    }

    @Test
    fun `command includes configured compose project name`() {
        val properties = DockerComposeRuntimeInfrastructureProperties().apply {
            composeFile = "../docker-compose-runtime-agent.yml"
            projectName = "federation-learning-runtime-agent"
        }

        val command = dockerComposeCommand(properties, listOf("up", "-d", "federation-learning-runtime-agent"))

        assertEquals(
            listOf(
                "docker",
                "compose",
                "-p",
                "federation-learning-runtime-agent",
                "-f",
                "../docker-compose-runtime-agent.yml",
                "up",
                "-d",
                "federation-learning-runtime-agent"
            ),
            command
        )
    }

    @Test
    fun `command omits project name when blank`() {
        val properties = DockerComposeRuntimeInfrastructureProperties().apply {
            composeFile = "../docker-compose-runtime-agent.yml"
            projectName = ""
        }

        val command = dockerComposeCommand(properties, listOf("config", "--services"))

        assertEquals(
            listOf("docker", "compose", "-f", "../docker-compose-runtime-agent.yml", "config", "--services"),
            command
        )
    }
}
