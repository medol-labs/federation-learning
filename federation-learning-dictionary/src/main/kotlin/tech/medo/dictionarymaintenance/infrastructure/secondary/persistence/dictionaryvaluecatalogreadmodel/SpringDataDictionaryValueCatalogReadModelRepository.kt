package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionaryvaluecatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum;


interface SpringDataDictionaryValueCatalogReadModelRepository : JpaRepository<DictionaryValueCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<DictionaryValueCatalogReadModelEntity> {

}
