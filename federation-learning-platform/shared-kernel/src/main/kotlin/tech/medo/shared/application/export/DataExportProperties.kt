package tech.medo.shared.application.export

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "medol.data-exchange.export")
class DataExportProperties {
    var pageSize: Int = 1000
    var asyncThreshold: Long = 10000
    var maxRows: Long = 800000
    var storagePath: String = "build/data-exports"
    var workerBatchSize: Int = 10
    var workerFixedDelayMs: Long = 5000
}
