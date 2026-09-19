package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s

import io.fabric8.kubernetes.api.model.APIResource
import io.fabric8.kubernetes.api.model.GenericKubernetesResource
import io.fabric8.kubernetes.api.model.Node
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.dsl.base.ResourceDefinitionContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration
import java.time.Instant

@Component
class Fabric8K3sCommandRunner(private val client: KubernetesClient) : K3sCommandRunner {
    override fun run(properties: K3sRuntimeInfrastructureProperties, arguments: List<String>): K3sCommandResult =
        runCatching {
            when {
                arguments == listOf("version", "--client") -> success("fabric8-kubernetes-client")
                arguments.take(2) == listOf("get", "nodes") -> listReadyNodes(arguments)
                arguments.firstOrNull() == "apply" -> applyManifest(properties, arguments)
                arguments.take(2) == listOf("rollout", "status") -> awaitDeployment(properties, arguments)
                else -> failure("Unsupported Kubernetes operation: ${arguments.joinToString(" ")}")
            }
        }.getOrElse { ex ->
            log.warn("Kubernetes API operation failed arguments={}", arguments, ex)
            failure(ex.message ?: ex.javaClass.name)
        }

    private fun listReadyNodes(arguments: List<String>): K3sCommandResult {
        val selector = argumentAfter(arguments, "-l")
        val parts = selector?.split("=", limit = 2)?.takeIf { it.size == 2 }
            ?: return failure("A key=value node selector is required.")
        val names = client.nodes().withLabel(parts[0], parts[1]).list().items
            .filter(::isReady)
            .mapNotNull { it.metadata?.name }
            .joinToString("\n") { "node/$it" }
        return success(names)
    }

    private fun applyManifest(properties: K3sRuntimeInfrastructureProperties, arguments: List<String>): K3sCommandResult {
        val namespace = argumentAfter(arguments, "-n") ?: properties.namespace
        val manifest = argumentAfter(arguments, "-f") ?: return failure("Manifest file is required.")
        val items = Files.newInputStream(Path.of(manifest)).use { input ->
            client.load(input).items()
        }
        items.forEach { item ->
            if (item is GenericKubernetesResource) {
                client.genericKubernetesResources(resourceDefinitionContext(item))
                    .inNamespace(namespace)
                    .resource(item)
                    .createOrReplace()
            } else {
                client.resource(item)
                    .inNamespace(namespace)
                    .createOrReplace()
            }
        }
        return success("Applied ${items.size} resources from $manifest in namespace $namespace")
    }

    private fun awaitDeployment(properties: K3sRuntimeInfrastructureProperties, arguments: List<String>): K3sCommandResult {
        val namespace = argumentAfter(arguments, "-n") ?: properties.namespace
        val name = arguments.getOrNull(2)?.substringAfter("deployment/")?.takeIf { it.isNotBlank() }
            ?: return failure("Deployment name is required.")
        val timeout = properties.commandTimeout.coerceAtLeast(Duration.ofSeconds(1))
        val deadline = Instant.now().plus(timeout)
        while (Instant.now().isBefore(deadline)) {
            val deployment = client.apps().deployments().inNamespace(namespace).withName(name).get()
            val desired = deployment?.spec?.replicas ?: 1
            if (deployment != null && (deployment.status?.availableReplicas ?: 0) >= desired) {
                return success("deployment/$name successfully rolled out")
            }
            Thread.sleep(500)
        }
        return K3sCommandResult(-1, "Deployment $name did not become ready within $timeout.", true)
    }

    private fun resourceDefinitionContext(item: GenericKubernetesResource): ResourceDefinitionContext {
        val (group, version) = apiGroupAndVersion(item.apiVersion)
        val kind = item.kind
        val resource = APIResource().apply {
            this.group = group
            this.version = version
            this.kind = kind
            this.name = pluralResourceName(kind)
            this.singularName = kind.replaceFirstChar { it.lowercase() }
            this.namespaced = true
        }
        return ResourceDefinitionContext.fromApiResource(item.apiVersion, resource)
    }

    private fun apiGroupAndVersion(apiVersion: String): Pair<String, String> {
        val parts = apiVersion.split("/", limit = 2)
        return if (parts.size == 1) "" to parts[0] else parts[0] to parts[1]
    }

    private fun pluralResourceName(kind: String): String {
        val lower = kind.replaceFirstChar { it.lowercase() }
        return when {
            lower.endsWith("s") || lower.endsWith("x") || lower.endsWith("ch") || lower.endsWith("sh") -> "${lower}es"
            lower.endsWith("y") -> "${lower.dropLast(1)}ies"
            else -> "${lower}s"
        }
    }

    private fun isReady(node: Node): Boolean = node.status?.conditions.orEmpty().any {
        it.type.equals("Ready", ignoreCase = true) && it.status.equals("True", ignoreCase = true)
    }

    private fun argumentAfter(arguments: List<String>, option: String): String? =
        arguments.indexOf(option).takeIf { it >= 0 }?.let { arguments.getOrNull(it + 1) }

    private fun success(output: String) = K3sCommandResult(0, output, false)

    private fun failure(output: String) = K3sCommandResult(1, output, false)

    companion object {
        private val log = LoggerFactory.getLogger(Fabric8K3sCommandRunner::class.java)
    }
}

interface K3sCommandRunner {
    fun run(properties: K3sRuntimeInfrastructureProperties, arguments: List<String>): K3sCommandResult
}

data class K3sCommandResult(
    val exitCode: Int,
    val output: String,
    val timedOut: Boolean
) {
    val succeeded: Boolean = exitCode == 0 && !timedOut
}
