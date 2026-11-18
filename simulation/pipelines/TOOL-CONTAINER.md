# Simulation Tool Delivery

The tool image is separate from all business and training images. It embeds the
public Simulation, Operations, bootstrap scripts and synthetic fixtures selected
by `simulation/toolkit/config.json`. No credentials or existing instance records
are delivered. The generic implementation is generated from `es-code-generator`.

## Connected Build Machine

From the Federation Learning project root:

```sh
node simulation/toolkit/package.mjs simulation/toolkit/config.json tmp/simulation-tool-20261118 --build
```

This configuration targets **linux/arm64**, including `fl-test-4` (192.168.50.4).
The build host can be AMD64 with BuildKit emulation; the tool verifies the target
architecture before execution. Use a new image version and output directory for
later snapshots. No platform, agent or engine rebuild is implied.

Transfer only the output's `simulation-tool.tar`, `SHA256SUMS`, `manifest.json`,
`image-reference.txt` and `run.sh`. Keep this delivery directory outside the
execution workspace.

## Offline Linux Host

```sh
cd /home/h3c/fl/k3d/delivery/toolkit-20261118
sha256sum -c SHA256SUMS
docker load -i simulation-tool.tar
export SIMULATION_TOOL_IMAGE="$(cat image-reference.txt)"
export SIMULATION_WORKSPACE=/home/h3c/fl/k3d/workspace
sh ./run.sh init
sh ./run.sh exec node --version
sh ./run.sh exec k3d version
sh ./run.sh exec kubectl version --client
```

No host Node.js installation is needed. The operator still needs access to Docker.
The launcher does not install Docker, grant permissions, change sysctl, create a
cluster or deploy services implicitly. `init` creates only a public workspace.

For the 50.4 delivery, all copied inputs and runtime state are rooted at
`/home/h3c/fl/k3d`. Business archives are copied to `delivery/` first, then loaded,
retagged and pushed to `192.168.50.4:5000` on that host. The tool's registry tag is
`192.168.50.4:5000/fl/simulation-tool:20261118`. The root `run-simulation.sh`
delivery launcher selects that image and the `workspace/` directory. Docker's
daemon storage and the existing Zot storage are not relocated by this procedure.

Before deployment, configure the target host addresses, registry, cluster name and
explicit kubeconfig in that workspace. **The existing `fl-test-twenty` definitions
refer to 192.168.50.2 and must not be used unchanged on 50.4.** Use new environment,
deployment-instance and run IDs; do not copy 50.2's runtime registry records.
The 50.4 deployment and fully disconnected image inventory are separate follow-up
steps, not validated by successful construction of this tool image.

## Manual Stages

Use the existing [twenty-participant workflow](TWENTY-PARTICIPANTS.md) after adapting
its environment/deployment declarations. Replace each host Node invocation:

| Existing command | Tool command |
| --- | --- |
| `node simulation/src/prepare-environment.mjs ...` | `sh run.sh environment ...` |
| `node simulation/src/instance-registry.mjs ...` | `sh run.sh registry ...` |
| `node simulation/src/task.mjs ...` | `sh run.sh task ...` |
| `node simulation/src/cases.mjs ...` | `sh run.sh cases ...` |
| `node simulation/extensions/scenarios/collect-training-evidence.mjs RUN` | `sh run.sh exec node simulation/extensions/scenarios/collect-training-evidence.mjs RUN` |

Export `MEDOL_PIPELINE_RUN_ID`, `MEDOL_SIMULATION_RUN_ID` and
`MEDOL_DEPLOYMENT_CONFIG` as in that workflow; the launcher passes these explicit
references, not credentials. Workspace `.env` and private state are read by the
same existing scripts. Baseline initialization, case data and test execution
remain separate tasks. Participant authentication still follows enrollment.

Artifacts remain under `$SIMULATION_WORKSPACE/simulation/reports/`; use normal
file transfer or removable media to retrieve them. Containers are disposable,
workspaces and credentials are not. Preserve the exact tool image alongside the
workspace for subsequent offline maintenance.

An intranet registry containing the application, engine and Kubernetes images is
currently required by this project's digest-verifying deployment adapter. A tool
tar alone is not an offline application deployment bundle. Refer to the generic
[tool guide](../toolkit/README.md) for image inventory and security boundaries.
Do not run the test credential generator or synthetic data tasks against production.
