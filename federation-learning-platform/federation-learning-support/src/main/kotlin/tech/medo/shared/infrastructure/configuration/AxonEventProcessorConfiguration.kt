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
    fun automationFileUploadExpireFileEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-file-upload-expire-file").notCustomized()

    @Bean
    fun integrationFileUploadExpireFileEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-file-upload-expire-file").notCustomized()

    @Bean
    fun readmodelDictionaryCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-dictionary-catalog").notCustomized()

    @Bean
    fun readmodelDictionaryValueCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-dictionary-value-catalog").notCustomized()

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
    fun readmodelServiceAccountApiTokenCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-service-account-api-token-catalog").notCustomized()

    @Bean
    fun readmodelUploadedFileCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-uploaded-file-catalog").notCustomized()

    @Bean
    fun readmodelUserAccountCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-user-account-catalog").notCustomized()

    @Bean
    fun readmodelUserRoleAssignmentCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-user-role-assignment-catalog").notCustomized()
}
