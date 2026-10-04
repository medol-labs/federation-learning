package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanCommand
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanInput
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanService
import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModelRepository
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelRepository
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructurePackageStateEnum




@Component
class CreateRuntimeInstallationPlanCommandHandler(
    private val decision: CreateRuntimeInstallationPlanDecision,
    private val createRuntimeInstallationPlanService: CreateRuntimeInstallationPlanService,
    private val organizationDirectoryReadModelRepository: OrganizationDirectoryReadModelRepository,
    private val runtimeInfrastructurePackageCatalogReadModelRepository: RuntimeInfrastructurePackageCatalogReadModelRepository
) {
    @CommandHandler
    fun handle(
        command: CreateRuntimeInstallationPlanCommand,
        eventAppender: EventAppender
    ) {
        val organizationDirectoryReadModelSelection = organizationDirectoryReadModelRepository.findById(command.organizationId)
        require(organizationDirectoryReadModelSelection != null && organizationDirectoryReadModelSelection.state == OrganizationStateEnum.Active) {
            "Organization Directory selection is not eligible for Create Runtime Installation Plan."
        }
        val runtimeInfrastructurePackageCatalogReadModelSelection = runtimeInfrastructurePackageCatalogReadModelRepository.findById(command.runtimeInfrastructurePackageId)
        require(runtimeInfrastructurePackageCatalogReadModelSelection != null && runtimeInfrastructurePackageCatalogReadModelSelection.state == RuntimeInfrastructurePackageStateEnum.Registered) {
            "Runtime Infrastructure Package Catalog selection is not eligible for Create Runtime Installation Plan."
        }
        val input = CreateRuntimeInstallationPlanInput(runtimeInstallationPlanId = command.runtimeInstallationPlanId, runtimeInfrastructureId = command.runtimeInfrastructureId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount)
        val portResult = createRuntimeInstallationPlanService.execute(input)

        eventAppender.append(decision.decide(command, portResult))
    }
}
