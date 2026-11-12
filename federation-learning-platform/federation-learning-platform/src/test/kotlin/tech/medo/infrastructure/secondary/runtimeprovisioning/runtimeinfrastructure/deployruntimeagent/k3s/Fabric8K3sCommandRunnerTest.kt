package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s

import io.fabric8.kubernetes.api.model.PersistentVolumeClaimBuilder
import io.fabric8.kubernetes.client.KubernetesClient
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.RETURNS_DEEP_STUBS
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

class Fabric8K3sCommandRunnerTest {
    @TempDir
    lateinit var directory: Path

    @Test
    fun `apply patches desired PVC fields instead of replacing server assigned fields`() {
        val client = mock(KubernetesClient::class.java, RETURNS_DEEP_STUBS)
        val claim = PersistentVolumeClaimBuilder().withNewMetadata().withName("data").endMetadata().build()
        `when`(client.load(any(InputStream::class.java)).items()).thenReturn(listOf(claim))
        val manifest = Files.writeString(directory.resolve("resources.yaml"), "kind: PersistentVolumeClaim")
        val resource = client.resource(claim).inNamespace("test")

        val result = Fabric8K3sCommandRunner(client).run(
            K3sRuntimeInfrastructureProperties(),
            listOf("apply", "-n", "test", "-f", manifest.toString())
        )

        assertTrue(result.succeeded, result.output)
        verify(resource.fieldManager("medol-runtime-agent-deployer")).serverSideApply()
        verify(resource, never()).createOrReplace()
    }
}
