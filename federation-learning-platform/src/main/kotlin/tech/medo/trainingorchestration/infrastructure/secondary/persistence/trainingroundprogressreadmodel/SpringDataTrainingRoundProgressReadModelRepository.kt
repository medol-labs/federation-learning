package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingroundprogressreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;

import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelKey

interface SpringDataTrainingRoundProgressReadModelRepository : JpaRepository<TrainingRoundProgressReadModelEntity, TrainingRoundProgressReadModelKey> {
    fun findAllByTrainingJobId(trainingJobId: UUID): List<TrainingRoundProgressReadModelEntity>
    fun findAllByRoundId(roundId: UUID): List<TrainingRoundProgressReadModelEntity>
}
