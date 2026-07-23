package tech.medo.external

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "runtime-agent", url = "\${external.runtime-agent.endpoint:}")
interface RuntimeAgentClient {
    @PostMapping("/request-dataset-access")
    fun requestDatasetAccess(@RequestBody payload: Map<String, Any?>): Any?
}
