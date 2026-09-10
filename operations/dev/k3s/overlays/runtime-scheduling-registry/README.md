# Federation Learning Runtime Registry Overlay

This overlay extends the generated `../../environments/dev-registry` environment and adds the Federation Learning runtime scheduling patches. The generated environment rewrites application images; this overlay only rewrites runtime scheduler image configuration that is not part of generated application Deployments.

Use it after pushing images to the registry:

```bash
kubectl apply -k overlays/runtime-scheduling-registry
kubectl -n federation-learning-platform get pods,svc,pvc
```

The generated `dev-registry` environment rewrites:

- `medol/federation-learning-platform-console`
- `medol/federation-learning-support`
- `medol/federation-learning-platform`
- `medol/federation-learning-runtime-agent`
This overlay rewrites:

- `medol/federation-learning-runtime-engine` references inside scheduler ConfigMaps
- `medol/federation-learning-runtime-agent` references inside scheduler ConfigMaps

If the registry address changes, regenerate the generated `dev-registry` environment from `.medol/medol.yaml`, then update `patches/runtime-registry-image-config.yaml` for the scheduler ConfigMap references.
