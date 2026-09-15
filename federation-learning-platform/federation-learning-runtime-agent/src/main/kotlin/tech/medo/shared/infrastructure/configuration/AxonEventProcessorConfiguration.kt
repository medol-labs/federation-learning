package tech.medo.shared.infrastructure.configuration

import org.axonframework.extension.spring.config.EventProcessorDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AxonEventProcessorConfiguration {
    @Bean
    fun auditTrailEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("audit-trail").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsAcceptExecutionPlanEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-accept-execution-plan").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsCompleteRoundExecutionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-complete-round-execution").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsFailRoundExecutionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-fail-round-execution").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsObserveRuntimeEngineJobEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-observe-runtime-engine-job").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsProfileAgentDatasetEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-profile-agent-dataset").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsReceiveParticipantExecutionPlanEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-receive-participant-execution-plan").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsReleaseRuntimeEngineJobAfterCompletionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-release-runtime-engine-job-after-completion").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsReleaseRuntimeEngineJobAfterFailureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-release-runtime-engine-job-after-failure").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsReleaseRuntimeEngineJobAfterRetryFailureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-release-runtime-engine-job-after-retry-failure").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsReleaseRuntimeEngineJobAfterRuntimeRetryFailureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-release-runtime-engine-job-after-runtime-retry-failure").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsReleaseRuntimeEngineJobAfterStartFailureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-release-runtime-engine-job-after-start-failure").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsReportRuntimeAgentStartedEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-report-runtime-agent-started").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsReportRuntimeInstanceConnectedEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-report-runtime-instance-connected").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsReportRuntimeInstanceSelfCheckPassedEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-report-runtime-instance-self-check-passed").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsStartRoundExecutionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-start-round-execution").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsSubmitAgentLocalModelUpdateEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-submit-agent-local-model-update").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsValidateAgentDatasetAccessEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-validate-agent-dataset-access").notCustomized()

    @Bean
    fun automationRuntimeAgentOperationsValidateDatasetContractEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-agent-operations-validate-dataset-contract").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsAcceptExecutionPlanEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-accept-execution-plan").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsCompleteRoundExecutionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-complete-round-execution").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsFailRoundExecutionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-fail-round-execution").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsObserveRuntimeEngineJobEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-observe-runtime-engine-job").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsProfileAgentDatasetEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-profile-agent-dataset").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsReceiveParticipantExecutionPlanEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-receive-participant-execution-plan").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsReleaseRuntimeEngineJobAfterCompletionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-release-runtime-engine-job-after-completion").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsReleaseRuntimeEngineJobAfterFailureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-release-runtime-engine-job-after-failure").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsReleaseRuntimeEngineJobAfterRetryFailureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-release-runtime-engine-job-after-retry-failure").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsReleaseRuntimeEngineJobAfterRuntimeRetryFailureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-release-runtime-engine-job-after-runtime-retry-failure").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsReleaseRuntimeEngineJobAfterStartFailureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-release-runtime-engine-job-after-start-failure").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsReportRuntimeAgentStartedEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-report-runtime-agent-started").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsReportRuntimeInstanceConnectedEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-report-runtime-instance-connected").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsReportRuntimeInstanceSelfCheckPassedEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-report-runtime-instance-self-check-passed").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsStartRoundExecutionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-start-round-execution").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsSubmitAgentLocalModelUpdateEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-submit-agent-local-model-update").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsValidateAgentDatasetAccessEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-validate-agent-dataset-access").notCustomized()

    @Bean
    fun integrationRuntimeAgentOperationsValidateDatasetContractEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-agent-operations-validate-dataset-contract").notCustomized()

    @Bean
    fun readmodelAgentDatasetAccessValidationCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-agent-dataset-access-validation-catalog").notCustomized()

    @Bean
    fun readmodelAgentFeatureSchemaCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-agent-feature-schema-catalog").notCustomized()

    @Bean
    fun readmodelAgentOrganizationDirectoryEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-agent-organization-directory").notCustomized()

    @Bean
    fun readmodelAgentRuntimeIdentityCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-agent-runtime-identity-catalog").notCustomized()

    @Bean
    fun readmodelAgentRuntimeInfrastructureConnectionCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-agent-runtime-infrastructure-connection-catalog").notCustomized()

    @Bean
    fun readmodelAgentRuntimeNodeInventoryCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-agent-runtime-node-inventory-catalog").notCustomized()

    @Bean
    fun readmodelAgentRuntimeNodeResourceLatestEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-agent-runtime-node-resource-latest").notCustomized()

    @Bean
    fun readmodelAgentRuntimeTelemetryLatestEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-agent-runtime-telemetry-latest").notCustomized()

    @Bean
    fun readmodelDatasetCapabilityEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-dataset-capability").notCustomized()

    @Bean
    fun readmodelDatasetReadinessEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-dataset-readiness").notCustomized()

    @Bean
    fun readmodelPermissionCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-permission-catalog").notCustomized()

    @Bean
    fun readmodelRoleCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-role-catalog").notCustomized()

    @Bean
    fun readmodelRolePermissionGrantCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-role-permission-grant-catalog").notCustomized()

    @Bean
    fun readmodelRoundExecutionCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-round-execution-catalog").notCustomized()

    @Bean
    fun readmodelRuntimeAgentLifecycleCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-agent-lifecycle-catalog").notCustomized()

    @Bean
    fun readmodelRuntimeDatasetBindingCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-dataset-binding-catalog").notCustomized()

    @Bean
    fun readmodelServiceAccountApiTokenCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-service-account-api-token-catalog").notCustomized()

    @Bean
    fun readmodelUserAccountCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-user-account-catalog").notCustomized()

    @Bean
    fun readmodelUserRoleAssignmentCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-user-role-assignment-catalog").notCustomized()
}
