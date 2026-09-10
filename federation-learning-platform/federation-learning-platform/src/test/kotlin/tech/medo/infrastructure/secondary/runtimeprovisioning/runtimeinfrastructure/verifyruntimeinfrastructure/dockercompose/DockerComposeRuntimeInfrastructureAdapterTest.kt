package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.verifyruntimeinfrastructure.dockercompose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.DockerComposeCommandResult
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.DockerComposeCommandRunner
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.DockerComposeDeployRuntimeAgentAdapter
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.DockerComposeRuntimeInfrastructureProperties
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.RuntimeProvisioningLookup
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
import java.time.Duration
import java.util.UUID

class DockerComposeRuntimeInfrastructureAdapterTest {
    private val runtimeInfrastructureId = UUID.fromString("00000000-0000-0000-0000-000000000001")
    private val runtimeInfrastructurePackageId = UUID.fromString("00000000-0000-0000-0000-000000000002")
    private val runtimeAgentId = UUID.fromString("00000000-0000-0000-0000-000000000003")
    private val runtimeInstallationPlanId = UUID.fromString("00000000-0000-0000-0000-000000000004")

    @Test
    fun `verify succeeds when docker compose version and config succeed`() {
        val runner = FakeDockerComposeCommandRunner(
            DockerComposeCommandResult(exitCode = 0, output = "Docker Compose version v2.40.0", timedOut = false),
            DockerComposeCommandResult(exitCode = 0, output = "federation-learning-runtime-agent", timedOut = false)
        )
        val adapter = DockerComposeVerifyRuntimeInfrastructureAdapter(properties(), lookup(), runner)

        val result = adapter.verify(
            verificationInput()
        )

        val succeeded = assertInstanceOf(RuntimeInfrastructureVerification.Succeeded::class.java, result)
        assertEquals("AUTO", succeeded.agentInstallMode)
        assertEquals(1, succeeded.observedNodeCount)
        assertEquals(listOf(listOf("version"), listOf("config", "--services")), runner.calls)
    }

    @Test
    fun `verify is unavailable when docker compose version cannot run`() {
        val runner = FakeDockerComposeCommandRunner(
            DockerComposeCommandResult(exitCode = 1, output = "docker not found", timedOut = false)
        )
        val adapter = DockerComposeVerifyRuntimeInfrastructureAdapter(properties(), lookup(), runner)

        val result = adapter.verify(
            verificationInput()
        )

        val unavailable = assertInstanceOf(RuntimeInfrastructureVerification.Unavailable::class.java, result)
        assertTrue(unavailable.failureReason.contains("docker compose version failed with exitCode=1"))
        assertEquals(listOf(listOf("version")), runner.calls)
    }

    @Test
    fun `deploy starts the configured runtime agent service`() {
        val runner = FakeDockerComposeCommandRunner(
            DockerComposeCommandResult(exitCode = 0, output = "started", timedOut = false)
        )
        val adapter = DockerComposeDeployRuntimeAgentAdapter(properties(), lookup(), runner)

        val result = adapter.execute(
            deployInput()
        )

        val succeeded = assertInstanceOf(DeployRuntimeAgentResult.Succeeded::class.java, result)
        assertEquals("test-agent-version", succeeded.agentVersion)
        assertEquals(listOf(listOf("up", "-d", "runtime-agent-test")), runner.calls)
    }

    @Test
    fun `adapter does not support non docker compose runtime infrastructure`() {
        val adapter = DockerComposeDeployRuntimeAgentAdapter(
            properties(),
            lookup(
                runtimePackage = runtimePackage(
                    runtimeEnvironmentType = "KUBERNETES"
                )
            ),
            FakeDockerComposeCommandRunner()
        )

        val supported = adapter.supports(
            deployInput()
        )

        assertFalse(supported)
    }

    private fun properties(): DockerComposeRuntimeInfrastructureProperties =
        DockerComposeRuntimeInfrastructureProperties().apply {
            composeFile = "docker-compose.test.yml"
            agentServiceName = "runtime-agent-test"
            agentVersion = "test-agent-version"
            commandTimeout = Duration.ofSeconds(5)
        }

    private fun verificationInput(): RuntimeInfrastructureVerificationInput =
        RuntimeInfrastructureVerificationInput(
            runtimeInfrastructureId = runtimeInfrastructureId,
            runtimeInstallationPlanId = runtimeInstallationPlanId,
            organizationId = UUID.fromString("00000000-0000-0000-0000-000000000005"),
            organizationName = "Test Organization",
            runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
            runtimeInfrastructurePackageName = "Docker Compose Runtime Package",
            runtimeInfrastructurePackageVersion = "1.0.0",
            runtimeEnvironmentType = "DOCKER_COMPOSE",
            runtimeName = "local runtime",
            expectedNodeCount = 1,
            runtimeAgentId = runtimeAgentId
        )

    private fun deployInput(): DeployRuntimeAgentInput =
        DeployRuntimeAgentInput(
            runtimeAgentId = runtimeAgentId,
            runtimeInfrastructureId = runtimeInfrastructureId,
            runtimeInstallationPlanId = runtimeInstallationPlanId,
            organizationId = UUID.fromString("00000000-0000-0000-0000-000000000005"),
            organizationName = "Test Organization",
            runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
            runtimeInfrastructurePackageName = "Docker Compose Runtime Package",
            runtimeInfrastructurePackageVersion = "1.0.0",
            runtimeEnvironmentType = "DOCKER_COMPOSE",
            runtimeName = "local runtime",
            agentInstallMode = "AUTO",
            expectedNodeCount = 1
        )

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
            organizationId = null,
            organizationName = null,
            runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
            runtimeInfrastructurePackageName = null,
            runtimeInfrastructurePackageVersion = null,
            runtimeName = "local runtime",
            agentInstallMode = "AUTO",
            expectedNodeCount = 1,
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
        runtimeEnvironmentType: String? = "DOCKER_COMPOSE"
    ): RuntimeInfrastructurePackageCatalogReadModel =
        RuntimeInfrastructurePackageCatalogReadModel(
            runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
            packageName = "local-runtime",
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

private class FakeDockerComposeCommandRunner(
    vararg results: DockerComposeCommandResult
) : DockerComposeCommandRunner {
    private val results = ArrayDeque(results.toList())
    val calls = mutableListOf<List<String>>()

    override fun run(
        properties: DockerComposeRuntimeInfrastructureProperties,
        arguments: List<String>
    ): DockerComposeCommandResult {
        calls += arguments
        return results.removeFirstOrNull()
            ?: error("No fake docker compose result was configured for arguments=$arguments")
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
