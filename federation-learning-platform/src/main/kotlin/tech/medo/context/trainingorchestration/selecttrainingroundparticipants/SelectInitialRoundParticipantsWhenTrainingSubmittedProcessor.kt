package tech.medo.trainingorchestration.selecttrainingroundparticipants

import tech.medo.trainingorchestration.trainingparticipanteligibility.TrainingParticipantEligibilityReadModel
import tech.medo.trainingorchestration.trainingparticipanteligibility.TrainingParticipantEligibilityReadModelRepository
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SelectInitialRoundParticipantsWhenTrainingSubmittedProcessor(
    private val repository: TrainingParticipantEligibilityReadModelRepository,
    private val commandGateway: CommandGateway
) {
    @Scheduled(fixedDelayString = "\${automation.select-initial-round-participants-when-training-submitted.fixed-delay-ms:5000}")
    fun processTodo() {
        repository.findAll(PageRequest.of(0, 100))
            .content
            .asSequence()
            .filter { todo -> todo.selectionReady == true && todo.trainingJobId != null }
            .forEach { todo ->
                commandGateway.send(SelectTrainingRoundParticipantsCommand(trainingJobId = todo.trainingJobId!!))
            }
    }
}
