package tech.medo.dataexchange.dataexportjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat


@Command
data class RequestDataExportCommand(
    val dataExportJobId: UUID,
    val resourceName: String,
    val criteriaJson: String,
    val sortJson: String,
    val columnsJson: String,
    val requestedLocale: String?,
    val requestedAt: LocalDateTime,
    val snapshotUpperBound: LocalDateTime,
    val requestHash: String,
    val fileName: String,
    val status: String
) {
    @TargetEntityId
    val selection: DataExportJobSelection = DataExportJobSelection(dataExportJobId = dataExportJobId)


}
