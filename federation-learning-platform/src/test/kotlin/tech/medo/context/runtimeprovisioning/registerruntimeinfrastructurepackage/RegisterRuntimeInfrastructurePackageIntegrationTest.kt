package tech.medo.runtimeprovisioning.registerruntimeinfrastructurepackage

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimeprovisioning.registerruntimeinfrastructurepackage.RegisterRuntimeInfrastructurePackageCommand
import java.util.UUID;

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:register-runtime-infrastructure-package-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class RegisterRuntimeInfrastructurePackageIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun RegisterRuntimeInfrastructurePackageintegration() {
        val command = RegisterRuntimeInfrastructurePackageCommand(
            runtimeInfrastructurePackageId = java.util.UUID.randomUUID(),
            packageName = "",
            packageVersion = "",
            runtimeEnvironmentType = "",
            runtimeDeploymentTargetType = "",
            installProfile = "",
            architecture = "",
            installGuide = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}
