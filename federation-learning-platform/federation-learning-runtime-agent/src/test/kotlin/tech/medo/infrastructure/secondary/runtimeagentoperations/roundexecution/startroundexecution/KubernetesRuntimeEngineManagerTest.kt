package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import io.fabric8.kubernetes.client.KubernetesClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class KubernetesRuntimeEngineManagerTest {
    @Test
    fun `engine keeps artifact URLs from the execution plan instead of rewriting gateway paths`() {
        val client = mock(KubernetesClient::class.java)
        val properties = LocalRuntimeEngineProperties(kubernetesNamespace = "participant")
        val manager = KubernetesRuntimeEngineManager(properties, client, "test-token")
        val container = manager.engineContainer("example.test/engine:sklearn")
        assertEquals("example.test/engine:sklearn", container.image)
        val environment = container.env.associate { it.name to it.value }
        assertEquals("test-token", environment["MEDOL_SECURITY_INTERNAL_TOKEN"])
        assertFalse(environment.containsKey("RUNTIME_ENGINE_FILE_ACCESS_BASE_URL"))
    }
}
