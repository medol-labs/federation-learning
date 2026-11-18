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

## Verification Scope

The approved project case uses twenty independent runtime agents and two rounds,
synthetic CSV data, the LogisticRegression model plugin and FedAvg aggregation.
Required evidence includes twenty selected participants, forty successful local
executions, both rounds completed, and accessible valid global model artifacts.
It is a functional scale test on one physical machine, not a twenty-machine
performance benchmark or proof of production cryptographic secure aggregation.
Record actual assertions and failures; deployment alone is not a passed test.
