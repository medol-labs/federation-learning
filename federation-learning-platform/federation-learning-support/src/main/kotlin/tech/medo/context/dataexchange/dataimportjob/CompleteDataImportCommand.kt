package tech.medo.dataexchange.dataimportjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import java.util.UUID;


@Command
data class CompleteDataImportCommand(
    val dataImportJobId: UUID,
    val totalRows: Long,
    val successRows: Long,
    val failedRows: Long,
    val failureIndexPath: String?,
    val status: String
) {

    @TargetEntityId
    val selection: DataImportJobSelection = DataImportJobSelection(dataImportJobId = dataImportJobId)



}
