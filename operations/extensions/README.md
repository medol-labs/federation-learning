# Project Deployment Packages

This directory contains hand-maintained deployment inputs. Keep concrete project
service names, routes, port mappings, Secret references and scheduling rules here,
not in the Simulation generator. These files contain no secret values.

`test-credentials.json` declares named random credentials and their env/JSON
output bindings using {"$secret":"name"} references. Change environment-specific
paths and Secret names in this file, not JavaScript. Generated values are retained
in private worker state and reused; conflicting existing credentials fail rather
than being replaced. The Simulation secrets task selects this configuration.

`participant-package.json` describes one repeatable participant deployment:

- resources and patches reference generated Operations manifests;
- images map workload image names to supplied registry references;
- namespaceTemplate and endpointTemplate describe instance identity and addressing;
- public/private patches select external gateway exposure;
- secrets explicitly lists credentials copied from the platform namespace;
- readiness lists deployments to wait for;
- enrollment declares the identity ConfigMap, immutable identity keys and pod scheduling.

Simulation's generated `kubernetes-instances.mjs` renders and executes this package.
It does not know the meaning of any application environment variable or registration
identifier. The small project adapter chooses this package; business registration
still supplies identity values. Labels and tolerations remain project policy.

Bindings use `{{name}}`, with no executable expressions. Supported render inputs
are environment, parentNamespace, namespace, slot, endpoint, service, and public
access fields when enabled. Enrollment additionally supplies nodeName.
Missing bindings fail. A whole-value binding retains its type (for example nodePort).

Update this package when generated resources are renamed. Validate with:

```sh
node --test simulation/extensions/adapters/participant-slots.test.mjs
```

Run from the repository root. Tests render Kustomize without applying to a cluster.
The package is Kubernetes-specific, not a Compose package or a production secret
distribution policy. Copying credentials between test namespaces is explicit here.
