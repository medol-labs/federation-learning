# FederationLearningPlatform - Network Topology (test)

Sites are configured deployment locations, not inferred physical hosts or subnets. Solid arrows stay within a site; dashed arrows cross site boundaries. Arrows describe configured routes and dependencies, not observed traffic or verified firewall access.

```mermaid
flowchart LR
  N1["Client / Browser"]
  subgraph Site1["Platform environment"]
    N2["apisix :9080"]
    N3["federation-learning-console :80"]
    N5["federation-learning-platform :8081"]
    N7["federation-learning-support :8080"]
    N8["postgres :5432"]
    N9["umadb :50051"]
  end
  subgraph Site2["Participant environment (per organization)"]
    N2["apisix :9080"]
    N4["federation-learning-participant-console :80"]
    N6["federation-learning-runtime-agent :8082"]
    N10["postgres :5432"]
    N11["umadb :50051"]
  end
  N1 --> N2
  N2 --> N3
  N2 -.->|configured cross-site route| N4
  N2 --> N5
  N2 -.->|configured cross-site route| N6
  N2 --> N7
  N5 --> N8
  N5 --> N9
  N6 --> N10
  N6 --> N11
  N7 --> N8
  N7 --> N9
  N5 -.->|HTTP / Participant execution dispatch| N6
  N6 -.->|HTTP / Runtime reporting and model updates| N5
```

## Deployment checks

- Gateway route /federation-learning-participant-console/* targets federation-learning-participant-console in participant; a routable upstream is required. A container service name alone will not resolve across sites.
- Gateway route /api/federation-learning-runtime-agent/* targets federation-learning-runtime-agent in participant; a routable upstream is required. A container service name alone will not resolve across sites.

This diagram does not assign IP addresses or verify network isolation. Generated single-stack Compose/Kubernetes manifests must be split or configured separately for the stated site placement.
