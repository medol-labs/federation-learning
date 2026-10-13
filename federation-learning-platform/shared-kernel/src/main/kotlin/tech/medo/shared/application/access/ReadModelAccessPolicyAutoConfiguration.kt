package tech.medo.shared.application.access

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@AutoConfiguration
class ReadModelAccessPolicyAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(ReadModelAccessPolicyFilter::class)
    fun readModelAccessPolicyFilter(): ReadModelAccessPolicyFilter = PermitAllReadModelAccessPolicyFilter()
}
