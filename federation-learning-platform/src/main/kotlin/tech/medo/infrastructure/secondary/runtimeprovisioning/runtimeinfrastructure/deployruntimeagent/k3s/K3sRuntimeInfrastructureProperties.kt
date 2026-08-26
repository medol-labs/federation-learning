package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "platform.runtime.k3s")
class K3sRuntimeInfrastructureProperties {
    var enabled: Boolean = true
    var kubectlExecutable: String = "kubectl"
    var kubeconfig: String? = null
    var namespace: String = "default"
    var agentManifestFile: String = "../k3s/runtime-agent.yaml"
    var agentDeploymentName: String = "federation-learning-runtime-agent"
    var agentVersion: String = "k3s"
    var commandTimeout: Duration = Duration.ofSeconds(60)
    var supportedEnvironmentTypes: List<String> = listOf("K3S", "KUBERNETES")
}
