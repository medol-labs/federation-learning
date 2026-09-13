package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import io.fabric8.kubernetes.api.model.ContainerBuilder
import io.fabric8.kubernetes.api.model.ServiceBuilder
import io.fabric8.kubernetes.api.model.TolerationBuilder
import io.fabric8.kubernetes.api.model.VolumeBuilder
import io.fabric8.kubernetes.api.model.VolumeMountBuilder
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder
import io.fabric8.kubernetes.client.KubernetesClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant

@Component
class KubernetesRuntimeEngineManager(
    private val properties: LocalRuntimeEngineProperties,
    private val client: KubernetesClient
) : RuntimeEngineResourceManager {
    override fun ensureStarted(jobId: String) {
        val name = properties.kubernetesResourceName(jobId)
        val labels = mapOf("app" to name, "medol.dev/runtime-engine-job-id" to jobId)
        val container = ContainerBuilder()
            .withName("runtime-engine")
            .withImage(properties.kubernetesImage)
            .withImagePullPolicy(properties.kubernetesImagePullPolicy)
            .addNewPort().withName("http").withContainerPort(properties.kubernetesContainerPort).endPort()
            .addNewEnv().withName("RUNTIME_ENGINE_ROOT").withValue(properties.runtimeRoot).endEnv()
            .addToVolumeMounts(
                VolumeMountBuilder().withName("datasets").withMountPath(properties.datasetContainerRoot).withReadOnly(true).build(),
                VolumeMountBuilder().withName("runtime-work").withMountPath(properties.runtimeRoot).build()
            )
            .build()
        val podSpec = DeploymentBuilder()
            .withNewMetadata().withName(name).withNamespace(properties.kubernetesNamespace).addToLabels(labels).endMetadata()
            .withNewSpec()
            .withReplicas(1)
            .withNewSelector().addToMatchLabels(labels).endSelector()
            .withNewTemplate()
            .withNewMetadata().addToLabels(labels).endMetadata()
            .withNewSpec()
            .withServiceAccountName(properties.kubernetesServiceAccountName)
            .addToNodeSelector(nodeSelector())
            .withTolerations(
                TolerationBuilder()
                    .withKey("medol.dev/runtime-only")
                    .withOperator("Equal")
                    .withValue("true")
                    .withEffect("NoSchedule")
                    .build()
            )
            .withContainers(container)
            .withVolumes(
                VolumeBuilder()
                    .withName("datasets")
                    .withNewHostPath()
                    .withPath(properties.kubernetesDatasetHostPath)
                    .withType("DirectoryOrCreate")
                    .endHostPath()
                    .build(),
                VolumeBuilder()
                    .withName("runtime-work")
                    .withNewHostPath()
                    .withPath(properties.kubernetesRuntimeHostPath)
                    .withType("DirectoryOrCreate")
                    .endHostPath()
                    .build()
            )
            .endSpec()
            .endTemplate()
            .endSpec()
            .build()
        val service = ServiceBuilder()
            .withNewMetadata().withName(name).withNamespace(properties.kubernetesNamespace).addToLabels(labels).endMetadata()
            .withNewSpec()
            .addToSelector(labels)
            .addNewPort()
            .withName("http")
            .withPort(properties.kubernetesContainerPort)
            .withNewTargetPort(properties.kubernetesContainerPort)
            .endPort()
            .endSpec()
            .build()

        client.apps().deployments().inNamespace(properties.kubernetesNamespace).resource(podSpec).createOrReplace()
        client.services().inNamespace(properties.kubernetesNamespace).resource(service).createOrReplace()
        awaitReady(name)
        log.info(
            "Runtime engine Kubernetes resources are ready. jobId={}, deployment={}, namespace={}",
            jobId,
            name,
            properties.kubernetesNamespace
        )
    }

    override fun delete(jobId: String) {
        val name = properties.kubernetesResourceName(jobId)
        client.services().inNamespace(properties.kubernetesNamespace).withName(name).delete()
        client.apps().deployments().inNamespace(properties.kubernetesNamespace).withName(name).delete()
        log.info("Deleted runtime engine Kubernetes resources. jobId={}, name={}", jobId, name)
    }

    private fun nodeSelector(): Map<String, String> = buildMap {
        put("medol.dev/node-role", "runtime")
        properties.kubernetesRuntimeInfrastructureId.takeIf { it.isNotBlank() }?.let {
            put("medol.dev/runtime-infrastructure-id", it)
        }
    }

    private fun awaitReady(name: String) {
        val timeout = properties.healthTimeout.coerceAtLeast(Duration.ofSeconds(1))
        val deadline = Instant.now().plus(timeout)
        while (Instant.now().isBefore(deadline)) {
            val deployment = client.apps().deployments().inNamespace(properties.kubernetesNamespace).withName(name).get()
            if ((deployment?.status?.availableReplicas ?: 0) >= 1) return
            Thread.sleep(properties.healthPollInterval.coerceAtLeast(Duration.ofMillis(200)).toMillis())
        }
        throw IllegalStateException("Runtime engine Deployment $name did not become ready within $timeout.")
    }

    companion object {
        private val log = LoggerFactory.getLogger(KubernetesRuntimeEngineManager::class.java)
    }
}

interface RuntimeEngineResourceManager {
    fun ensureStarted(jobId: String)

    fun delete(jobId: String)
}
