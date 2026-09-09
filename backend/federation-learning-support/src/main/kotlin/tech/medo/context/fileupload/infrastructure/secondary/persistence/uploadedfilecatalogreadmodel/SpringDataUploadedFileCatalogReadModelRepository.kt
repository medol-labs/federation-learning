package tech.medo.fileupload.infrastructure.secondary.persistence.uploadedfilecatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.fileupload.domain.states.UploadedFileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


interface SpringDataUploadedFileCatalogReadModelRepository : JpaRepository<UploadedFileCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<UploadedFileCatalogReadModelEntity> {

}
