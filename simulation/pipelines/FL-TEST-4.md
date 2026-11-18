# Offline Twenty-Participant Test on FL Test 4

All delivery files, configuration and evidence live under `/home/h3c/fl/k3d`.
The host needs Docker and a POSIX shell, not Node.js. The tool container runs the
existing Simulation stages against the persistent `workspace` subdirectory.

## Prerequisites

Import the tool archive and business/infrastructure archives under `delivery/`,
verify their `SHA256SUMS`, then publish on this host to `192.168.50.4:5000`.
Keep `images.json`, `content-verification.json` and `published-images.json` for
provenance. Images are ARM64; business images reuse the previously tested versions.

The infrastructure bundle contains K3s `v1.33.5-k3s1`, K3d proxy/tools `5.8.3`,
CoreDNS, metrics-server, local-path-provisioner, BusyBox and pause images. Host
Docker needs the original K3d tags; cluster image pulls use the intranet Zot mirror.
The tool image is not itself a complete offline cluster bundle.

An administrator must raise the host inotify limits before starting 21 K3s nodes:

```sh
sudo sysctl -w fs.inotify.max_user_instances=8192 fs.inotify.max_user_watches=1048576
```

This setting is temporary. Persist it through the host administrator's approved
sysctl policy before rebooting a maintained environment. Do not restart Docker or
delete Zot to apply it. Default limits of 128 instances have caused kubelet startup
to fail with `inotify_init: too many open files` on this host.

## Manual Stages

The delivered `/home/h3c/fl/k3d/stages.sh` is a copy of `fl-test-4-stages.sh`.
Execute one stage at a time and check its exit code before proceeding:

```sh
cd /home/h3c/fl/k3d
sh stages.sh environment
sh stages.sh resolve
sh stages.sh secrets
sh stages.sh deploy
sh stages.sh register
sh stages.sh create-run
sh stages.sh select-cases
sh stages.sh prepare-baseline
sh stages.sh enroll-participants
sh stages.sh prepare-participant-auth
sh stages.sh prepare-case
sh stages.sh test
sh stages.sh evidence
```

Registry records and successful task outputs are create-only. Do not repeat a
completed stage; inspect its report. Set `FL_TEST_RUN_ID` for a new test execution
against the same registered instance. Set `FL_DEPLOYMENT_RUN_ID` for a new deployment
attempt; do not erase failed reports. Never reuse registration after deleting the
cluster: the recorded infrastructure identity must match.

Configuration uses `simulation/environments/fl-test-4-twenty.json`,
`simulation/deployments/fl-test-4-twenty.json` and
`simulation/pipelines/deployment-twenty-fl-test-4.json`. It does not alter the 50.2
environment. The namespace is `federation-learning-platform`; participant namespaces
have suffixes `-p1` through `-p20`.

Platform UI: `http://192.168.50.4:31080`.
Participant UIs: `http://192.168.50.4:31081` through `http://192.168.50.4:31100`.
These endpoints become available only after deployment succeeds.

Credentials are generated in `workspace/simulation/test-environment.private.json`
and `workspace/.env`; never commit or publish them. Run evidence is under
`workspace/simulation/reports/workflows/<run-id>/`.

## Restarting the Existing Installation

Do not rerun data initialization or instance registration after a host restart.
The maintained environment entry starts the existing cluster and checks static
node addresses, API readiness and every Kubernetes node:

```sh
cd /home/h3c/fl/k3d
sh run-simulation.sh exec node simulation/src/prepare-environment.mjs simulation/environments/fl-test-4-twenty.json
sh run-simulation.sh exec kubectl --kubeconfig operations/k3s/.work/kubeconfig-federation-learning-twenty.yaml get deployments -A
```

Allow application startup probes to finish before logging in. Node readiness
does not imply application readiness. For a planned shutdown, stop the cluster
before powering off the host:

```sh
sh run-simulation.sh exec k3d cluster stop federation-learning-twenty
```

On 2026-11-18 the existing 22 node/proxy container endpoints were migrated to
explicit static Docker addresses without replacing the cluster or volumes.
The migration snapshot is `workspace/simulation/reports/network-before-static-20261118.json`.
The previous `recover-node-ips-20261118.sh` status-patching script is not a normal
startup command. See `simulation/DEPLOYMENT.md` for the generic network check and
explicit migration procedure. Updated scripts are in the workspace; the original
tool archive predates this change, so include the updated workspace when making
a new offline delivery.

## Verification Scope

The approved project case uses twenty independent runtime agents and two rounds,
synthetic CSV data, the LogisticRegression model plugin and FedAvg aggregation.
Required evidence includes twenty selected participants, forty successful local
executions, both rounds completed, and accessible valid global model artifacts.
It is a functional scale test on one physical machine, not a twenty-machine
performance benchmark or proof of production cryptographic secure aggregation.
Record actual assertions and failures; deployment alone is not a passed test.
