package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.submitagentlocalmodelupdate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.LocalRuntimeEngineProperties
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateInput
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateResult
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Path
import java.util.UUID

class PlatformSubmitAgentLocalModelUpdateAdapterTest {
    @Test
    fun submitsLocalModelUpdateToPlatformWithCalculatedDigest(@TempDir tempDir: Path) {
        val artifactPath = tempDir.resolve("job-1/local-runtime/local_update.json")
        Files.createDirectories(artifactPath.parent)
        Files.writeString(artifactPath, """{"weights":[0.1,0.2],"bias":0.3}""")
        val client = RecordingClient()
        val fileUploadClient = RecordingFileUploadClient()
        val adapter = adapter(
            client = client,
            fileUploadClient = fileUploadClient,
            runtimeEngineProperties = LocalRuntimeEngineProperties(
                runtimeRoot = "/workspace/tmp/runtime-engine",
                runtimeRootHostRoot = tempDir.toString()
            )
        )

        val result = adapter.execute(
            input(
                artifactRef = "/workspace/tmp/runtime-engine/job-1/local-runtime/local_update.json",
                artifactDigest = ""
            )
        )

        assertTrue(result is SubmitAgentLocalModelUpdateResult.Succeeded)
        val request = client.requests.single()
        assertEquals(UUID.fromString("11111111-1111-4111-8111-111111111111"), request.modelUpdateSubmissionId)
        assertTrue(request.secureAggregationRequired)
        assertEquals(UUID.fromString("cccccccc-cccc-4ccc-8ccc-cccccccccccc"), request.secureAggregationSessionId)
        assertEquals("PAILLIER", request.encryptionScheme)
        assertEquals("local-dev-v1", request.publicKeyVersion)
        assertEquals("http://support/api/files/bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb/content", request.artifactRef)
        assertEquals("sha256:stored", request.artifactDigest)
        assertEquals("HOMOMORPHIC_ENCRYPTED", request.updateProtectionType)
        assertEquals(BigDecimal("0.125"), request.trainingLoss)
        assertEquals(artifactPath, fileUploadClient.paths.single())
    }

    @Test
    fun usesStoredArtifactDigestAsTheCanonicalDigest(@TempDir tempDir: Path) {
        val artifactPath = tempDir.resolve("local_update.json")
        Files.writeString(artifactPath, "{}")
        val client = RecordingClient()
        val adapter = adapter(client = client, fileUploadClient = RecordingFileUploadClient())

        adapter.execute(input(artifactRef = artifactPath.toString(), artifactDigest = "sha256:provided"))

        assertEquals("sha256:stored", client.requests.single().artifactDigest)
    }

    private fun adapter(
        client: PlatformModelUpdateSubmissionClient,
        fileUploadClient: SupportFileUploadClient,
        properties: LocalModelUpdateSubmissionProperties = LocalModelUpdateSubmissionProperties(enabled = true),
        runtimeEngineProperties: LocalRuntimeEngineProperties = LocalRuntimeEngineProperties()
    ): PlatformSubmitAgentLocalModelUpdateAdapter =
        PlatformSubmitAgentLocalModelUpdateAdapter(
            client = client,
            properties = properties,
            runtimeEngineProperties = runtimeEngineProperties,
            fileUploadClient = fileUploadClient
        )

    private fun input(
        artifactRef: String = "/tmp/local_update.json",
        artifactDigest: String = ""
    ): SubmitAgentLocalModelUpdateInput =
        SubmitAgentLocalModelUpdateInput(
            modelUpdateSubmissionId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            executionSessionId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            executionPlanId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            roundExecutionId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            trainingJobId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            trainingRunConfigurationId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            roundId = UUID.fromString("77777777-7777-4777-8777-777777777777"),
            runtimeId = UUID.fromString("88888888-8888-4888-8888-888888888888"),
            featureSchemaId = UUID.fromString("99999999-9999-4999-8999-999999999999"),
            secureAggregationRequired = true,
            secureAggregationSessionId = UUID.fromString("cccccccc-cccc-4ccc-8ccc-cccccccccccc"),
            encryptionScheme = "PAILLIER",
            publicKeyVersion = "local-dev-v1",
            runtimeEngineJobId = "runtime-engine-job-1",
            localModelId = UUID.fromString("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"),
            updateArtifactId = UUID.fromString("bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb"),
            artifactRef = artifactRef,
            artifactDigest = artifactDigest,
            updateProtectionType = "HOMOMORPHIC_ENCRYPTED",
            trainingLoss = BigDecimal("0.125")
        )

    private class RecordingClient : PlatformModelUpdateSubmissionClient {
        val requests = mutableListOf<SubmitModelUpdateSubmissionRequest>()

        override fun submitModelUpdateSubmission(
            request: SubmitModelUpdateSubmissionRequest
        ): SubmitModelUpdateSubmissionResponse {
            requests += request
            return SubmitModelUpdateSubmissionResponse(modelUpdateSubmissionId = request.modelUpdateSubmissionId)
        }
    }

    private class RecordingFileUploadClient : SupportFileUploadClient {
        val paths = mutableListOf<Path>()

        override fun upload(fileId: UUID, path: Path): UploadedFile {
            paths.add(path)
            return UploadedFile(
                fileId = fileId,
                fileLocation = "http://support/api/files/$fileId/content",
                originalFileName = path.fileName.toString(),
                contentType = "application/json",
                sizeBytes = Files.size(path),
                checksum = "sha256:stored"
            )
        }
    }
}
