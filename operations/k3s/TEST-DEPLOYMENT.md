# Test K3s deployment

The generated test overlay is `environments/test`. It uses the same Kubernetes
namespace and resource names as dev. Deploy test to a separate cluster; applying
both overlays to one cluster will update the same workloads and PVCs.

## Before deployment

1. Review `operations/config/common.yml` and the generated test ConfigMap,
   especially frontend API URLs and browser CORS origins. The test overlay does
   not enable the dev-only localhost CORS defaults. Put environment-specific
   non-secret overrides in `operations/config/test.yml`, for example:

   ```yaml
   environments:
     test:
       applicationOverrides:
         federation-learning-platform:
           environmentVariables:
             - name: MEDOL_SECURITY_ALLOWED_ORIGINS
               value: https://console.example.test
   ```

   Replace the example origin with the real browser origin. Configure frontend
   API/auth URLs for the actual gateway before building and deploying images;
   the currently generated test ConfigMap still contains a development
   Supabase URL.
2. Choose an image source. For an image registry, copy
   `operations/config/test.private.example.yml` to ignored
   `operations/config/test.private.yml`, set the reachable registry address,
   and run `bash operations/scripts/prepare-registry.sh test`.
   Apply the locally prepared
   `environments/test-registry` overlay. Otherwise, import the exact images
   referenced by `environments/test` into every node that may run them.
3. Create real test Secrets from `environments/test/secrets.example.yaml` or
   an external secret manager. Replace all example passwords, JWT keys, and
   internal tokens. Do not commit real Secret manifests.
4. Ensure K3s system images, CoreDNS, local-path-provisioner, PostgreSQL,
   UmaDB, and APISIX are available to the cluster. Verify that the nodes can
   resolve the registry if registry mode is used.
5. Regenerate test operations after editing the committed `test.yml` or model
   topology. Editing only `test.private.yml` needs the shell preparation step,
   not a full regeneration. Review the resulting manifests before applying.

## Apply

Run from `operations/k3s` with `kubectl` pointing at the **test** cluster:

```bash
kubectl config current-context
kubectl create namespace federation-learning-platform --dry-run=client -o yaml | kubectl apply -f -
kubectl -n federation-learning-platform apply -f environments/test/secrets.test.yaml
kubectl apply -k environments/test
kubectl -n federation-learning-platform get pods,svc,pvc
```

Use `kubectl apply -k environments/test-registry` instead of `environments/test`
when a test registry overlay was generated. The Secret file is local and is
not included by Kustomize.

The `runtime-scheduling` component is application-specific. The current
`federation-learning-dev.sh` helper generates development credentials and
localhost URLs by default, so do not run it against test unchanged. Prepare
an environment-owned component with real Secrets, node mounts, image
references, and scheduler RBAC before platform-managed runtime-agent
deployment or runtime-engine scheduling.
