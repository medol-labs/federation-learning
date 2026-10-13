package tech.medo.shared.application.access

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.context.annotation.ImportCandidates
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class ReadModelAccessPolicyAutoConfigurationTest {
    private val contextRunner = ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(*ImportCandidates.load(
            AutoConfiguration::class.java, javaClass.classLoader
        ).candidates.filter { it.startsWith("tech.medo.shared.application.access.") }
            .map { Class.forName(it) }.toTypedArray()))

    @Test
    fun `discovers and registers the default filter`() {
        contextRunner.run { context ->
            assertThat(context).hasNotFailed()
            assertThat(context).hasSingleBean(ReadModelAccessPolicyFilter::class.java)
            assertThat(context.getBean(ReadModelAccessPolicyFilter::class.java))
                .isInstanceOf(PermitAllReadModelAccessPolicyFilter::class.java)
        }
    }

    @Test
    fun `backs off when a custom filter is registered`() {
        contextRunner.withUserConfiguration(CustomFilterConfiguration::class.java).run { context ->
            assertThat(context).hasNotFailed()
            assertThat(context).hasSingleBean(ReadModelAccessPolicyFilter::class.java)
            assertThat(context.getBean(ReadModelAccessPolicyFilter::class.java))
                .isSameAs(context.getBean("customFilter"))
        }
    }

    @Configuration(proxyBeanMethods = false)
    class CustomFilterConfiguration {
        @Bean
        fun customFilter(): ReadModelAccessPolicyFilter = PermitAllReadModelAccessPolicyFilter()
    }
}
