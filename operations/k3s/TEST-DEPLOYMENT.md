# Test K3s deployment

The generated test site entry points are `environments/test/sites/platform`
and `environments/test/sites/participant`. Each is independently deployable
and contains only that site's applications and infrastructure. Deploy test to
clusters separate from dev; resource names and PVC names are unchanged across
environments.

## Before deployment

1. Review `operations/config/common.yml` and both generated site ConfigMaps,
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
   Apply the matching `environments/test-registry/sites/<site>` overlay.
   Otherwise, import the exact images referenced by the corresponding
   `environments/test/sites/<site>` entry into eligible nodes.
3. Create separate real Secrets from each site's `secrets.example.yaml`, or
   use an external secret manager. Replace all example passwords, JWT keys,
   and internal tokens. Do not commit real Secret manifests.
4. Ensure K3s system images, CoreDNS, local-path-provisioner, PostgreSQL,
   UmaDB, and APISIX are available to the cluster. Verify that the nodes can
   resolve the registry if registry mode is used.
5. Regenerate test operations after editing the committed `test.yml` or model
   topology. Editing only `test.private.yml` needs the shell preparation step,
   not a full regeneration. Review the resulting manifests before applying.

## Apply

Prefer `node scripts/package-offline.mjs --environment test` from the repository
root so the matching Federation Learning scheduling component is rendered into
each site package. On each control node, apply only the received site package:

```bash
kubectl config current-context
kubectl create namespace federation-learning-platform --dry-run=client -o yaml | kubectl apply -f -
kubectl -n federation-learning-platform apply -f /secure/path/runtime-secrets.yaml
kubectl apply -f manifests/deployment.yaml
kubectl -n federation-learning-platform get pods,svc,pvc
```

For source-based validation before packaging, render
`environments/test/sites/<site>` or
`environments/test-registry/sites/<site>`. The real Secret is external and is
not included by Kustomize.

The `runtime-scheduling` component is application-specific and split into
`platform` and `participant` subcomponents. The package wrapper selects the
matching one. The root component and `federation-learning-dev.sh` are for the
combined local development topology and must not be used as the formal
split-site test release.
