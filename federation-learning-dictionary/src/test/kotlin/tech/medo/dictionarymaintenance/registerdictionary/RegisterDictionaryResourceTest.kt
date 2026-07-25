package tech.medo.dictionarymaintenance.registerdictionary

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.post
import java.util.UUID

@AutoConfigureMockMvc
@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:register-dictionary-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class RegisterDictionaryResourceTest(
    @Autowired private val mockMvc: MockMvc
) {
    @Test
    fun rejectsDuplicateDictionaryCode() {
        val dictionaryCode = "RUNTIME_CONNECTIVITY_MODE_${UUID.randomUUID().toString().replace("-", "_")}"

        val firstRequest = mockMvc.post("/dictionary/registerdictionary") {
            contentType = MediaType.APPLICATION_JSON
            content = registerDictionaryJson(dictionaryCode)
        }.andReturn()

        mockMvc.perform(asyncDispatch(firstRequest))
            .andExpect(status().isOk)

        val duplicateRequest = mockMvc.post("/dictionary/registerdictionary") {
            contentType = MediaType.APPLICATION_JSON
            content = registerDictionaryJson(dictionaryCode)
        }.andReturn()

        mockMvc.perform(asyncDispatch(duplicateRequest))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.detail").value("Code already exists."))
    }

    private fun registerDictionaryJson(dictionaryCode: String): String = """
        {
          "dictionaryId": "${UUID.randomUUID()}",
          "dictionaryCode": "$dictionaryCode",
          "dictionaryName": "$dictionaryCode",
          "description": "test dictionary"
        }
    """.trimIndent()
}
