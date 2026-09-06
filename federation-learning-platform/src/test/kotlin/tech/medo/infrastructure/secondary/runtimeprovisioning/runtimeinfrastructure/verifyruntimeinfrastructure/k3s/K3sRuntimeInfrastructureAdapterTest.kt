package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.verifyruntimeinfrastructure.k3s

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.RuntimeProvisioningLookup
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s.K3sCommandResult
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s.K3sCommandRunner
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s.K3sDeployRuntimeAgentAdapter
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s.K3sRuntimeInfrastructureProperties
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentInput
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentResult
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelCriteria
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelRepository
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModelCriteria
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModelRepository
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerificationInput
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration
import java.util.UUID

class K3sRuntimeInfrastructureAdapterTest {
    private val runtimeInfrastructureId = UUID.fromString("00000000-0000-0000-0000-000000000011")
    private val runtimeInfrastructurePackageId = UUID.fromString("00000000-0000-0000-0000-000000000012")
    private val runtimeAgentId = UUID.fromString("00000000-0000-0000-0000-000000000013")
    private val runtimeInstallationPlanId = UUID.fromString("00000000-0000-0000-0000-000000000014")
    private val organizationId = UUID.fromString("00000000-0000-0000-0000-000000000015")

    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `verify succeeds and counts observable k3s nodes`() {
        val runner = FakeK3sCommandRunner(
            K3sCommandResult(exitCode = 0, output = "Client Version: v1.34.0+k3s1", timedOut = false),
            K3sCommandResult(exitCode = 0, output = "node/server-1\nnode/worker-1\n", timedOut = false)
        )
        val adapter = K3sVerifyRuntimeInfrastructureAdapter(properties(), lookup(), runner)

        val result = adapter.verify(
            RuntimeInfrastructureVerificationInput(
                runtimeInfrastructureId = runtimeInfrastructureId,
                runtimeInstallationPlanId = runtimeInstallationPlanId,
                runtimeAgentId = runtimeAgentId
            )
        )

        val succeeded = assertInstanceOf(RuntimeInfrastructureVerification.Succeeded::class.java, result)
        assertEquals("AUTO", succeeded.agentInstallMode)
        assertEquals(2, succeeded.observedNodeCount)
        assertEquals(
            listOf(
                listOf("version", "--client"),
                listOf("get", "nodes", "-l", "medol.dev/runtime-infrastructure-id=$runtimeInfrastructureId", "-o", "name")
            ),
            runner.calls
        )
    }

    @Test
    fun `verify rejects when k3s cluster has no observable nodes`() {
        val runner = FakeK3sCommandRunner(
            K3sCommandResult(exitCode = 0, output = "Client Version: v1.34.0+k3s1", timedOut = false),
            K3sCommandResult(exitCode = 0, output = "", timedOut = false)
        )
        val adapter = K3sVerifyRuntimeInfrastructureAdapter(properties(), lookup(), runner)

        val result = adapter.verify(
            RuntimeInfrastructureVerificationInput(
                runtimeInfrastructureId = runtimeInfrastructureId,
                runtimeInstallationPlanId = runtimeInstallationPlanId,
                runtimeAgentId = runtimeAgentId
            )
        )

        val rejected = assertInstanceOf(RuntimeInfrastructureVerification.Rejected::class.java, result)
        assertEquals(0, rejected.observedNodeCount)
        assertEquals(
            "K3S cluster has no nodes labeled with medol.dev/runtime-infrastructure-id=$runtimeInfrastructureId.",
            rejected.failureReason
        )
    }

    @Test
    fun `deploy renders managed manifest and waits for runtime agent rollout`() {
        val runner = FakeK3sCommandRunner(
            K3sCommandResult(exitCode = 0, output = "configured", timedOut = false),
            K3sCommandResult(exitCode = 0, output = "deployment successfully rolled out", timedOut = false)
        )
        val adapter = K3sDeployRuntimeAgentAdapter(properties(), lookup(), runner)

        val result = adapter.execute(
            DeployRuntimeAgentInput(
                runtimeAgentId = runtimeAgentId,
                runtimeInfrastructureId = runtimeInfrastructureId
            )
        )

        val succeeded = assertInstanceOf(DeployRuntimeAgentResult.Succeeded::class.java, result)
        assertEquals("test-k3s-agent-version", succeeded.agentVersion)
        val manifestPath = tempDir.resolve("runtime-agent-test-000000000000.yaml").toString()
        assertEquals(
            listOf(
                listOf("apply", "-n", "runtime-test", "-f", manifestPath),
                listOf("rollout", "status", "deployment/runtime-agent-test-000000000000", "-n", "runtime-test")
            ),
            runner.calls
        )
        val manifest = Files.readString(tempDir.resolve("runtime-agent-test-000000000000.yaml"))
        assertTrue(manifest.contains("name: \"runtime-agent-test-000000000000\""))
        assertTrue(manifest.contains("name: \"RUNTIME_AGENT_ID\""))
        assertTrue(manifest.contains("value: \"$runtimeAgentId\""))
        assertTrue(manifest.contains("name: \"RUNTIME_INFRASTRUCTURE_ID\""))
        assertTrue(manifest.contains("value: \"$runtimeInfrastructureId\""))
        assertTrue(manifest.contains("name: \"RUNTIME_AGENT_INSTALL_MODE\""))
        assertTrue(manifest.contains("value: \"PLATFORM_MANAGED\""))
        assertTrue(manifest.contains("name: \"RUNTIME_AGENT_ORGANIZATION_ID\""))
        assertTrue(manifest.contains("value: \"$organizationId\""))
        assertTrue(manifest.contains("value: \"http://runtime-agent-test-000000000000:8082\""))
        assertTrue(manifest.contains("nodeSelector:"))
        assertTrue(manifest.contains("medol.dev/node-role: \"runtime\""))
        assertTrue(manifest.contains("medol.dev/runtime-infrastructure-id: \"$runtimeInfrastructureId\""))
        assertTrue(manifest.contains("key: \"medol.dev/runtime-only\""))
        assertTrue(manifest.contains("effect: \"NoSchedule\""))
    }

    @Test
    fun `deploy is unavailable when rollout fails`() {
        val runner = FakeK3sCommandRunner(
            K3sCommandResult(exitCode = 0, output = "configured", timedOut = false),
            K3sCommandResult(exitCode = 1, output = "timed out waiting for rollout", timedOut = false)
        )
        val adapter = K3sDeployRuntimeAgentAdapter(properties(), lookup(), runner)

        val result = adapter.execute(
            DeployRuntimeAgentInput(
                runtimeAgentId = runtimeAgentId,
                runtimeInfrastructureId = runtimeInfrastructureId
            )
        )

        val unavailable = assertInstanceOf(DeployRuntimeAgentResult.Unavailable::class.java, result)
        assertTrue(unavailable.failureReason.contains("kubectl rollout status deployment/runtime-agent-test-000000000000"))
        assertTrue(unavailable.failureReason.contains("exitCode=1"))
    }

    @Test
    fun `adapter does not support docker compose runtime infrastructure`() {
        val adapter = K3sDeployRuntimeAgentAdapter(
            properties(),
            lookup(
                runtimePackage = runtimePackage(
                    runtimeEnvironmentType = "DOCKER_COMPOSE"
                )
            ),
            FakeK3sCommandRunner()
        )

        val supported = adapter.supports(
            DeployRuntimeAgentInput(
                runtimeAgentId = runtimeAgentId,
                runtimeInfrastructureId = runtimeInfrastructureId
            )
        )

        assertFalse(supported)
    }

    private fun properties(): K3sRuntimeInfrastructureProperties =
        K3sRuntimeInfrastructureProperties().apply {
            namespace = "runtime-test"
            renderedManifestDirectory = tempDir.toString()
            agentDeploymentName = "runtime-agent-test"
            agentVersion = "test-k3s-agent-version"
            commandTimeout = Duration.ofSeconds(5)
        }

    private fun lookup(
        plan: RuntimeInstallationPlanCatalogReadModel = runtimeInstallationPlan(),
        runtimePackage: RuntimeInfrastructurePackageCatalogReadModel = runtimePackage()
    ): RuntimeProvisioningLookup =
        RuntimeProvisioningLookup(
            plans = FakeRuntimeInstallationPlanRepository(listOf(plan)),
            packages = FakeRuntimeInfrastructurePackageRepository(listOf(runtimePackage))
        )

    private fun runtimeInstallationPlan(): RuntimeInstallationPlanCatalogReadModel =
        RuntimeInstallationPlanCatalogReadModel(
            runtimeInstallationPlanId = runtimeInstallationPlanId,
            organizationId = organizationId,
            organizationName = null,
            runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
            runtimeInfrastructurePackageName = null,
            runtimeInfrastructurePackageVersion = null,
            runtimeName = "local k3s runtime",
            agentInstallMode = "AUTO",
            expectedNodeCount = 2,
            planStatus = null,
            runtimeInfrastructureId = runtimeInfrastructureId,
            preparedAt = null,
            preparedNodeCount = null,
            observedNodeCount = null,
            runtimeAgentId = runtimeAgentId,
            runtimeAgentVersion = null,
            plannedAt = null,
            verifiedAt = null,
            verificationFailedAt = null,
            verificationFailureReason = null,
            agentReadyAt = null,
            agentDeploymentFailedAt = null,
            agentDeploymentFailureReason = null,
            agentDeploymentRetryFailedAt = null,
            agentDeploymentRetryFailureReason = null,
            lastConnectedAt = null,
            userId = null,
            sessionId = null,
            correlationId = null,
            causationId = null,
            traceId = null,
            tenantId = null
        )

    private fun runtimePackage(
        runtimeEnvironmentType: String? = "K3S"
    ): RuntimeInfrastructurePackageCatalogReadModel =
        RuntimeInfrastructurePackageCatalogReadModel(
            runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
            packageName = "local-k3s-runtime",
            packageVersion = "1.0.0",
            runtimeEnvironmentType = runtimeEnvironmentType,
            state = null,
            userId = null,
            sessionId = null,
            correlationId = null,
            causationId = null,
            traceId = null,
            tenantId = null
        )
}

private class FakeK3sCommandRunner(
    vararg results: K3sCommandResult
) : K3sCommandRunner {
    private val results = ArrayDeque(results.toList())
    val calls = mutableListOf<List<String>>()

    override fun run(properties: K3sRuntimeInfrastructureProperties, arguments: List<String>): K3sCommandResult {
        calls += arguments
        return results.removeFirstOrNull()
            ?: error("No fake K3S result was configured for arguments=$arguments")
    }
}

private class FakeRuntimeInstallationPlanRepository(
    plans: List<RuntimeInstallationPlanCatalogReadModel>
) : RuntimeInstallationPlanCatalogReadModelRepository {
    private val plans = plans.associateBy { it.runtimeInstallationPlanId }

    override fun findAll(pageable: Pageable): Page<RuntimeInstallationPlanCatalogReadModel> =
        PageImpl(plans.values.toList())

    override fun findAllByCriteria(
        criteria: RuntimeInstallationPlanCatalogReadModelCriteria?,
        pageable: Pageable
    ): Page<RuntimeInstallationPlanCatalogReadModel> =
        findAll(pageable)

    override fun findById(id: UUID): RuntimeInstallationPlanCatalogReadModel? = plans[id]

    override fun findProjectionById(id: UUID): RuntimeInstallationPlanCatalogReadModelProjection? = null

    override fun save(projection: RuntimeInstallationPlanCatalogReadModelProjection) = Unit
}

private class FakeRuntimeInfrastructurePackageRepository(
    packages: List<RuntimeInfrastructurePackageCatalogReadModel>
) : RuntimeInfrastructurePackageCatalogReadModelRepository {
    private val packages = packages.associateBy { it.runtimeInfrastructurePackageId }

    override fun findAll(pageable: Pageable): Page<RuntimeInfrastructurePackageCatalogReadModel> =
        PageImpl(packages.values.toList())

    override fun findAllByCriteria(
        criteria: RuntimeInfrastructurePackageCatalogReadModelCriteria?,
        pageable: Pageable
    ): Page<RuntimeInfrastructurePackageCatalogReadModel> =
        findAll(pageable)

    override fun findById(id: UUID): RuntimeInfrastructurePackageCatalogReadModel? = packages[id]

    override fun findProjectionById(id: UUID): RuntimeInfrastructurePackageCatalogReadModelProjection? = null

    override fun save(projection: RuntimeInfrastructurePackageCatalogReadModelProjection) = Unit
}
