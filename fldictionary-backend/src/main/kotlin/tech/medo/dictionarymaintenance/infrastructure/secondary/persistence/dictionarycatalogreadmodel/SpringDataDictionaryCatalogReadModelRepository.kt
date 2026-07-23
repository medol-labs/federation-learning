package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionarycatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;


interface SpringDataDictionaryCatalogReadModelRepository : JpaRepository<DictionaryCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<DictionaryCatalogReadModelEntity> {

}
