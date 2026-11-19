package tech.medo.domain.trainingorchestration.recoverhistoricaltraininground

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.events.HistoricalTrainingRoundRecoveredEvent
import tech.medo.trainingorchestration.recoverhistoricaltraininground.*
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.util.HexFormat

@Component
class RecoverHistoricalTrainingRoundDecisionComponent(
    private val mapper: ObjectMapper,
    @Value("\${training-recovery.manifest:}") private val manifest: String,
    @Value("\${training-recovery.sha256:}") private val expectedDigest: String,
) : RecoverHistoricalTrainingRoundDecision {
    override fun decide(command: RecoverHistoricalTrainingRoundCommand, state: TrainingRoundState): List<Any> {
        require(manifest.isNotBlank() && expectedDigest.isNotBlank() && command.recoveryDigest == expectedDigest) { "No approved recovery manifest." }
        val bytes = Files.readAllBytes(Path.of(manifest))
        require(HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes)) == expectedDigest)
        val row = mapper.readTree(bytes).single {
            it.path("roundId").asText() == command.roundId.toString() && it.path("trainingJobId").asText() == command.trainingJobId.toString()
        } as com.fasterxml.jackson.databind.node.ObjectNode
        val event = mapper.treeToValue(row.put("recoveryDigest", expectedDigest), HistoricalTrainingRoundRecoveredEvent::class.java)
        if (state.recoveryDigest == expectedDigest) return emptyList()
        require(state.recoveryDigest == null && state.selectedRuntimeCount == null && state.acceptedRuntimeIds.isEmpty()) { "Cannot replace an existing round snapshot." }
        require(event.selectedParticipants.map { it.runtimeId }.toSet() == event.selectedRuntimeIds.toSet())
        require(event.selectedRuntimeIds.distinct().size == event.selectedRuntimeCount)
        require(event.recoverySubmissionIds.distinct().size == event.recoverySubmissionIds.size)
        return listOf(event)
    }
}
