package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingparticipanteligibilityreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import java.math.BigDecimal;


interface SpringDataTrainingParticipantEligibilityReadModelRepository : JpaRepository<TrainingParticipantEligibilityReadModelEntity, UUID>, JpaSpecificationExecutor<TrainingParticipantEligibilityReadModelEntity> {

}
