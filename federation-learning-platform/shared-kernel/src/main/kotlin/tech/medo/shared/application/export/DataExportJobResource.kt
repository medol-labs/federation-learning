package tech.medo.shared.application.export

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.Files
import java.nio.file.Path

@RestController("dataExportJobDownloadResource")
@RequestMapping("/data-export/jobs")
class DataExportJobResource(
    private val dataExportService: DataExportService
) {
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/download")
    fun download(
        @RequestParam filePath: String,
        @RequestParam fileName: String
    ): ResponseEntity<Any> {
        val path = Path.of(filePath).normalize()
        if (!Files.exists(path)) return ResponseEntity.notFound().build()
        return dataExportService.csvResponse(fileName, Files.readAllBytes(path))
    }
}
