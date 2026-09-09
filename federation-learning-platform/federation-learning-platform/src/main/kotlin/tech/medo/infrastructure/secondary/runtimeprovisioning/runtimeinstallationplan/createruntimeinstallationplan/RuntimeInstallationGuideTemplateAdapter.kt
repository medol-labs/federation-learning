package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinstallationplan.createruntimeinstallationplan

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanInput
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanResult
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanService
import java.util.UUID

@Component
class RuntimeInstallationGuideTemplateAdapter : CreateRuntimeInstallationPlanService {
    override fun execute(input: CreateRuntimeInstallationPlanInput): CreateRuntimeInstallationPlanResult =
        CreateRuntimeInstallationPlanResult.Succeeded(
            bootstrapCommand = renderBootstrapCommand(input.runtimeInfrastructureId, input.organizationId),
            nodeLabelCommand = renderNodeLabelCommand(input.runtimeInfrastructureId, input.organizationId),
            nodeTaintCommand = renderNodeTaintCommand(),
            runtimeAgentNodeSelectorYaml = renderRuntimeAgentNodeSelectorYaml(input.runtimeInfrastructureId),
            runtimeAgentTolerationsYaml = renderRuntimeAgentTolerationsYaml(),
            bootstrapConfigYaml = renderBootstrapConfigYaml(input.runtimeInfrastructureId, input.organizationId, input.runtimeName)
        )
}

private const val RUNTIME_NODE_ROLE_LABEL = "medol.dev/node-role"
private const val RUNTIME_NODE_ROLE_VALUE = "runtime"
private const val ORGANIZATION_LABEL = "medol.dev/organization-id"
private const val RUNTIME_INFRASTRUCTURE_LABEL = "medol.dev/runtime-infrastructure-id"
private const val RUNTIME_ONLY_TAINT = "medol.dev/runtime-only"

private fun renderBootstrapCommand(
    runtimeInfrastructureId: UUID,
    organizationId: UUID
): String =
    listOf(
        renderNodeLabelCommand(runtimeInfrastructureId, organizationId),
        renderNodeTaintCommand()
    ).joinToString("\n")

private fun renderNodeLabelCommand(
    runtimeInfrastructureId: UUID,
    organizationId: UUID
): String =
    "kubectl label node <node-name> " +
        "$RUNTIME_NODE_ROLE_LABEL=$RUNTIME_NODE_ROLE_VALUE " +
        "$ORGANIZATION_LABEL=$organizationId " +
        "$RUNTIME_INFRASTRUCTURE_LABEL=$runtimeInfrastructureId --overwrite"

private fun renderNodeTaintCommand(): String =
    "kubectl taint node <node-name> $RUNTIME_ONLY_TAINT=true:NoSchedule --overwrite"

private fun renderRuntimeAgentNodeSelectorYaml(runtimeInfrastructureId: UUID): String =
    """
    nodeSelector:
      $RUNTIME_NODE_ROLE_LABEL: "$RUNTIME_NODE_ROLE_VALUE"
      $RUNTIME_INFRASTRUCTURE_LABEL: "$runtimeInfrastructureId"
    """.trimIndent()

private fun renderRuntimeAgentTolerationsYaml(): String =
    """
    tolerations:
      - key: "$RUNTIME_ONLY_TAINT"
        operator: "Equal"
        value: "true"
        effect: "NoSchedule"
    """.trimIndent()

private fun renderBootstrapConfigYaml(
    runtimeInfrastructureId: UUID,
    organizationId: UUID,
    runtimeName: String
): String =
    """
    apiVersion: medol.dev/v1alpha1
    kind: RuntimeParticipantNodeConfig
    metadata:
      runtimeName: "$runtimeName"
    spec:
      runtimeInfrastructureId: "$runtimeInfrastructureId"
      organizationId: "$organizationId"
      node:
        labels:
          $RUNTIME_NODE_ROLE_LABEL: "$RUNTIME_NODE_ROLE_VALUE"
          $ORGANIZATION_LABEL: "$organizationId"
          $RUNTIME_INFRASTRUCTURE_LABEL: "$runtimeInfrastructureId"
        taints:
          - key: "$RUNTIME_ONLY_TAINT"
            value: "true"
            effect: "NoSchedule"
      runtimeAgent:
        ${renderRuntimeAgentNodeSelectorYaml(runtimeInfrastructureId).prependIndent("    ").trimStart()}
        ${renderRuntimeAgentTolerationsYaml().prependIndent("    ").trimStart()}
    """.trimIndent()
