package tech.medo.secureaggregation.infrastructure.secondary.persistence.secureaggregationsessioncatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


interface SpringDataSecureAggregationSessionCatalogReadModelRepository : JpaRepository<SecureAggregationSessionCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<SecureAggregationSessionCatalogReadModelEntity> {

}
