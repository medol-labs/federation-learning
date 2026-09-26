package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "platform.runtime.k3s")
class K3sRuntimeInfrastructureProperties {
    var enabled: Boolean = true
    var namespace: String = "default"
    var agentManifestFile: String? = null
    var renderedManifestDirectory: String = "target/runtime-agent-manifests"
    var agentDeploymentName: String = "federation-learning-runtime-agent"
    var agentImage: String = "medol/federation-learning-runtime-agent:0.0.1-SNAPSHOT"
    var agentReplicas: Int = 1
    var agentContainerPort: Int = 8082
    var agentAllowedOrigins: String = "http://localhost:*,http://127.0.0.1:*,http://*:30082"
    var agentSecurityProvider: String = "local"
    var agentAdminBootstrapEnabled: Boolean = true
    var agentSecuritySecretName: String = "federation-learning-runtime-agent-dev-secret"
    var agentJwtSecretKey: String = "MEDOL_SECURITY_JWT_SECRET"
    var agentInternalTokenKey: String = "MEDOL_SECURITY_INTERNAL_TOKEN"
    var agentAdminBootstrapSetupTokenKey: String = "MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN"
    var participantConsoleEnabled: Boolean = true
    var participantConsoleImage: String = "medol/federation-learning-participant-console:0.0.1-SNAPSHOT"
    var participantConsoleReplicas: Int = 1
    var participantConsoleContainerPort: Int = 80
    var participantConsoleServiceType: String = "NodePort"
    var participantConsoleNodePort: Int? = 30082
    var participantConsoleNodePortAllocationRange: Int = 1
    var participantConsoleApiPath: String = "/api/runtime-agent"
    var platformUrl: String = "http://federation-learning-platform:8081"
    var supportUrl: String = "http://federation-learning-support:8080"
    var databaseUrl: String = "jdbc:postgresql://postgres:5432/federation_learning_runtime_agent"
    var databaseSecretName: String = "postgres-secret"
    var databaseImage: String = "postgres:16"
    var databaseName: String = "federation_learning_runtime_agent"
    var databaseStorageSize: String = "1Gi"
    var umadbTarget: String = "umadb:50051"
    var umadbImage: String = "umadb/umadb:0.7.8"
    var umadbStorageSize: String = "1Gi"
    var endpointScope: String = "CLUSTER"
    var agentVersion: String = "k3s"
    var runtimeEngineServiceAccountName: String = "federation-learning-runtime-engine-scheduler"
    var runtimeEngineImage: String = "medol/federation-learning-runtime-engine:0.0.1-SNAPSHOT"
    var runtimeEngineImagePullPolicy: String = "IfNotPresent"
    var runtimeEngineContainerPort: Int = 8080
    var runtimeEngineDatasetHostPath: String = "/workspace/datasets"
    var runtimeEngineWorkHostPath: String = "/workspace/tmp/runtime-engine"
    var runtimeEngineHealthTimeout: Duration = Duration.ofMinutes(2)
    var runtimeEngineJobObservationTimeout: Duration = Duration.ofMinutes(10)
    var commandTimeout: Duration = Duration.ofSeconds(60)
    var supportedEnvironmentTypes: List<String> = listOf("K3S", "KUBERNETES")
}
