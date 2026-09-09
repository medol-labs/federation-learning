package tech.medo.dictionarymaintenance.adddictionaryvalue

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.dictionarymaintenance.adddictionaryvalue.AddDictionaryValueCommand
import java.util.UUID
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode
import tech.medo.dictionarymaintenance.domain.types.DisplayOrder

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:add-dictionary-value-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class AddDictionaryValueIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun AddDictionaryValueintegration() {
        val command = AddDictionaryValueCommand(
            dictionaryValueId = java.util.UUID.randomUUID(),
            dictionaryId = java.util.UUID.randomUUID(),
            dictionaryCode = DictionaryCode(""),
            valueCode = DictionaryValueCode(""),
            displayName = "",
            displayOrder = null,
            description = null,
            active = false
        )

        commandGateway.send(command).getResultMessage().join()
    }
}
