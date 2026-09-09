package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionarycatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.states.DictionaryStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


interface SpringDataDictionaryCatalogReadModelRepository : JpaRepository<DictionaryCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<DictionaryCatalogReadModelEntity> {

}
