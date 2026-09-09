package tech.medo.runtimeagentoperations.revalidateagentdatasetaccess

import java.util.UUID;

interface RevalidateAgentDatasetAccessService {
    fun supports(input: RevalidateAgentDatasetAccessInput): Boolean = true
    fun execute(input: RevalidateAgentDatasetAccessInput): RevalidateAgentDatasetAccessResult
}

data class RevalidateAgentDatasetAccessInput(
    val datasetAccessValidationId: UUID,
    val runtimeDatasetBindingId: UUID
)

sealed interface RevalidateAgentDatasetAccessResult {
    data class Succeeded(
        val readable: Boolean,
        val schemaReadable: Boolean,
        val sampleBatchReadable: Boolean
    ) : RevalidateAgentDatasetAccessResult

    data class Rejected(
        val failureReason: String
    ) : RevalidateAgentDatasetAccessResult

    data class Unavailable(
        val failureReason: String
    ) : RevalidateAgentDatasetAccessResult
}
