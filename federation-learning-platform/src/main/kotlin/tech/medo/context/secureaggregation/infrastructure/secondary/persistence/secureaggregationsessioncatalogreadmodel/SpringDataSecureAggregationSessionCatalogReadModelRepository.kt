package tech.medo.secureaggregation.infrastructure.secondary.persistence.secureaggregationsessioncatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataSecureAggregationSessionCatalogReadModelRepository : JpaRepository<SecureAggregationSessionCatalogReadModelEntity, UUID> {

}
