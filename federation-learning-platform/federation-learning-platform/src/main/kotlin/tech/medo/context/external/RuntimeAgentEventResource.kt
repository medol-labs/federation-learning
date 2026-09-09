package tech.medo.external

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/external/runtime-agent/events")
class RuntimeAgentEventResource {
    @PostMapping("/dataset-access-validated")
    fun datasetAccessValidated(@RequestBody payload: Map<String, Any?>): ResponseEntity<Void> =
        ResponseEntity.accepted().build()
}
