package tech.medo.trainingorchestration.domain.types

import tech.medo.trainingorchestration.domain.types.ModelUpdateReceipt;


data class RoundSubmissionLedger(
    val receipts: List<ModelUpdateReceipt>
)
