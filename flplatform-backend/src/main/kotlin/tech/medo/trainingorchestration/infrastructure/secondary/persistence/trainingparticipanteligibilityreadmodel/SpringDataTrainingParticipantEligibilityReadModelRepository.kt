package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingparticipanteligibilityreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataTrainingParticipantEligibilityReadModelRepository : JpaRepository<TrainingParticipantEligibilityReadModelEntity, UUID> {

}
