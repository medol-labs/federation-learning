package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.verifyruntimeinfrastructure.k3s

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
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
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelRepository
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModelRepository
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerificationInput
import java.time.Duration
import java.util.UUID

class K3sRuntimeInfrastructureAdapterTest {
    private val runtimeInfrastructureId = UUID.fromString("00000000-0000-0000-0000-000000000011")
    private val runtimeInfrastructurePackageId = UUID.fromString("00000000-0000-0000-0000-000000000012")
    private val runtimeAgentId = UUID.fromString("00000000-0000-0000-0000-000000000013")

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
                agentInstallMode = "MANUAL",
                observedNodeCount = 0
            )
        )

        val succeeded = assertInstanceOf(RuntimeInfrastructureVerification.Succeeded::class.java, result)
        assertEquals("AUTO", succeeded.agentInstallMode)
        assertEquals(2, succeeded.observedNodeCount)
        assertEquals(listOf(listOf("version", "--client"), listOf("get", "nodes", "-o", "name")), runner.calls)
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
                agentInstallMode = "MANUAL",
                observedNodeCount = 0
            )
        )

        val rejected = assertInstanceOf(RuntimeInfrastructureVerification.Rejected::class.java, result)
        assertEquals(0, rejected.observedNodeCount)
        assertEquals("K3S cluster has no observable nodes.", rejected.failureReason)
    }

    @Test
    fun `deploy applies manifest and waits for runtime agent rollout`() {
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
        assertEquals(runtimeAgentId, succeeded.runtimeAgentId)
        assertEquals("test-k3s-agent-version", succeeded.agentVersion)
        assertEquals(
            listOf(
                listOf("apply", "-n", "runtime-test", "-f", "runtime-agent-test.yaml"),
                listOf("rollout", "status", "deployment/runtime-agent-test", "-n", "runtime-test")
            ),
            runner.calls
        )
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
        assertTrue(unavailable.failureReason.contains("kubectl rollout status deployment/runtime-agent-test"))
        assertTrue(unavailable.failureReason.contains("exitCode=1"))
    }

    @Test
    fun `adapter does not support docker compose runtime infrastructure`() {
        val adapter = K3sDeployRuntimeAgentAdapter(
            properties(),
            lookup(
                runtimePackage = runtimePackage(
                    runtimeEnvironmentType = "DOCKER_COMPOSE",
                    runtimeDeploymentTargetType = "DOCKER_COMPOSE_HOST"
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
            agentManifestFile = "runtime-agent-test.yaml"
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
            runtimeInstallationPlanId = UUID.fromString("00000000-0000-0000-0000-000000000014"),
            organizationId = null,
            organizationName = null,
            runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
            runtimeInfrastructurePackageName = null,
            runtimeInfrastructurePackageVersion = null,
            runtimeName = "local k3s runtime",
            agentInstallMode = "AUTO",
            expectedNodeCount = 2,
            planStatus = null,
            runtimeInfrastructureId = runtimeInfrastructureId,
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
        runtimeEnvironmentType: String? = "K3S",
        runtimeDeploymentTargetType: String? = "K3S_CLUSTER"
    ): RuntimeInfrastructurePackageCatalogReadModel =
        RuntimeInfrastructurePackageCatalogReadModel(
            runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
            packageName = "local-k3s-runtime",
            packageVersion = "1.0.0",
            runtimeEnvironmentType = runtimeEnvironmentType,
            runtimeDeploymentTargetType = runtimeDeploymentTargetType,
            installProfile = null,
            architecture = null,
            installGuide = null,
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

    override fun findById(id: UUID): RuntimeInfrastructurePackageCatalogReadModel? = packages[id]

    override fun findProjectionById(id: UUID): RuntimeInfrastructurePackageCatalogReadModelProjection? = null

    override fun save(projection: RuntimeInfrastructurePackageCatalogReadModelProjection) = Unit
}
