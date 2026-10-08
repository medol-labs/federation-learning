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
    fun automationDataExchangeDataExportJobEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-data-exchange-data-export-job").notCustomized()

    @Bean
    fun automationDataExchangeDataImportJobEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-data-exchange-data-import-job").notCustomized()

    @Bean
    fun automationFileUploadExpireFileEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-file-upload-expire-file").notCustomized()

    @Bean
    fun integrationDataExchangeDataExportJobEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-data-exchange-data-export-job").notCustomized()

    @Bean
    fun integrationDataExchangeDataImportJobEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-data-exchange-data-import-job").notCustomized()

    @Bean
    fun integrationFileUploadExpireFileEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-file-upload-expire-file").notCustomized()

    @Bean
    fun readmodelDataExportJobCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-data-export-job-catalog").notCustomized()

    @Bean
    fun readmodelDataImportJobCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-data-import-job-catalog").notCustomized()

    @Bean
    fun readmodelDictionaryCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-dictionary-catalog").notCustomized()

    @Bean
    fun readmodelDictionaryValueCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-dictionary-value-catalog").notCustomized()

    @Bean
    fun readmodelDictionaryValueTranslationCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-dictionary-value-translation-catalog").notCustomized()

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
    fun readmodelSystemNotificationCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-system-notification-catalog").notCustomized()

    @Bean
    fun readmodelSystemNotificationSummaryEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-system-notification-summary").notCustomized()

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
