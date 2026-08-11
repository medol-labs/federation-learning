package tech.medo.infrastructure.secondary.trainingorchestration.traininground.starttraininground

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundInput
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundResult
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundService

@Component
class LocalStartTrainingRoundAdapter : StartTrainingRoundService {
    private val log = LoggerFactory.getLogger(LocalStartTrainingRoundAdapter::class.java)

    override fun supports(input: StartTrainingRoundInput): Boolean = true

    override fun execute(input: StartTrainingRoundInput): StartTrainingRoundResult {
        if (input.selectedRuntimeCount < input.minimumNodesPerRound) {
            return StartTrainingRoundResult.Rejected(
                failureReason = "Selected runtime count ${input.selectedRuntimeCount} is below required quorum ${input.minimumNodesPerRound}."
            )
        }
        if (input.selectedParticipants.isEmpty()) {
            return StartTrainingRoundResult.Rejected(
                failureReason = "Selected participant snapshot is empty."
            )
        }

        log.info(
            "Starting training round locally. trainingJobId={}, roundId={}, roundNumber={}, selectedRuntimeCount={}, minimumNodesPerRound={}",
            input.trainingJobId,
            input.roundId,
            input.roundNumber,
            input.selectedRuntimeCount,
            input.minimumNodesPerRound
        )
        return StartTrainingRoundResult.Succeeded()
    }
}
