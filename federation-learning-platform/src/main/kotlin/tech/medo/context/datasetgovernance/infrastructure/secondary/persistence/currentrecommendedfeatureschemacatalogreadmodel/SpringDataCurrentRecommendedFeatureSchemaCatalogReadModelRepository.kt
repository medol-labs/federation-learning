package tech.medo.datasetgovernance.infrastructure.secondary.persistence.currentrecommendedfeatureschemacatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


interface SpringDataCurrentRecommendedFeatureSchemaCatalogReadModelRepository : JpaRepository<CurrentRecommendedFeatureSchemaCatalogReadModelEntity, String>, JpaSpecificationExecutor<CurrentRecommendedFeatureSchemaCatalogReadModelEntity> {

}
