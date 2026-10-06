package tech.medo.dataexchange.dataexportjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import java.util.UUID


@Command
data class CompleteDataExportCommand(
    val dataExportJobId: UUID,
    val fileName: String,
    val filePath: String,
    val rowCount: Long,
    val status: String
) {
    @TargetEntityId
    val selection: DataExportJobSelection = DataExportJobSelection(dataExportJobId = dataExportJobId)


}
