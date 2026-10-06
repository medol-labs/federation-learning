package tech.medo.dataexchange.dataexportjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import java.time.Instant
import java.util.UUID;


@Command
data class RequestDataExportCommand(
    val dataExportJobId: UUID,
    val resourceName: String,
    val criteriaJson: String,
    val sortJson: String,
    val columnsJson: String,
    val requestedLocale: String?,
    val requestedAt: Instant,
    val snapshotUpperBound: Instant,
    val requestHash: String,
    val fileName: String,
    val status: String
) {
    @TargetEntityId
    val selection: DataExportJobSelection = DataExportJobSelection(dataExportJobId = dataExportJobId)


}
