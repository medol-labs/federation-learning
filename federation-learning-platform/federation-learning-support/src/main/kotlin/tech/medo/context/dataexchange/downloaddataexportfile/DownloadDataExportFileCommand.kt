package tech.medo.dataexchange.downloaddataexportfile

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.dataexchange.dataexportjob.DataExportJobSelection
import java.util.UUID;


@Command
data class DownloadDataExportFileCommand(
    val dataExportJobId: UUID,
    val fileName: String,
    val downloadUri: String
) {

    @TargetEntityId
    val selection: DataExportJobSelection = DataExportJobSelection(dataExportJobId = dataExportJobId)



}
