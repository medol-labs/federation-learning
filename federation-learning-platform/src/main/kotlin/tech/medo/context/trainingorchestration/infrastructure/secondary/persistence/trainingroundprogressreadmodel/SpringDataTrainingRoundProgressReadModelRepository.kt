package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingroundprogressreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;
import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelKey

interface SpringDataTrainingRoundProgressReadModelRepository : JpaRepository<TrainingRoundProgressReadModelEntity, TrainingRoundProgressReadModelKey>, JpaSpecificationExecutor<TrainingRoundProgressReadModelEntity> {
    fun findAllByTrainingJobId(trainingJobId: UUID): List<TrainingRoundProgressReadModelEntity>
    fun findAllByRoundId(roundId: UUID): List<TrainingRoundProgressReadModelEntity>
}
