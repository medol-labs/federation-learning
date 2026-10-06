package tech.medo.dictionarymaintenance.setdictionaryvaluetranslation

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.dictionarymaintenance.setdictionaryvaluetranslation.SetDictionaryValueTranslationCommand
import java.util.UUID
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode
import tech.medo.dictionarymaintenance.domain.types.LocaleCode

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:set-dictionary-value-translation-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class SetDictionaryValueTranslationIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun SetDictionaryValueTranslationintegration() {
        val command = SetDictionaryValueTranslationCommand(
            dictionaryValueTranslationId = java.util.UUID.randomUUID(),
            dictionaryValueId = java.util.UUID.randomUUID(),
            dictionaryCode = DictionaryCode(""),
            valueCode = DictionaryValueCode(""),
            locale = LocaleCode(""),
            displayName = "",
            description = null
        )

        commandGateway.send(command).getResultMessage().join()
    }
}
