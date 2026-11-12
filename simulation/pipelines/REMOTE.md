# Local build, remote K3d execution

Use `remote.json-example` as the starting point for a private `remote.json`.
Set `hosts.k3d.target` to an SSH config alias with key authentication and a verified
host key. Set `workDir` to a dedicated absolute directory owned by the remote user.
Do not use a production checkout: synchronization updates public worker inputs.

From the local project root:

```bash
node simulation/src/pipeline.mjs plan simulation/pipelines/remote.json
node simulation/src/pipeline.mjs run simulation/pipelines/remote.json
```

Build/publish steps execute locally. A whitelist of Operations configuration,
Simulation worker code, initialization scripts and dictionary fixtures is copied
using rsync without deletion. SSH then executes the Simulation stages remotely.
The remote worker is an executor, not a separate compilation environment or a call
to the old k3d training flow. API calls and Agent port forwarding happen remotely;
localhost addresses refer to the remote machine.

Prerequisites on the remote host: Node.js 20+, rsync, Docker, k3d, kubectl and curl.
The remote user must have Docker access. Configure the remote project `.env`
separately: dataset paths, registry settings, tokens and administrator credentials
must describe that machine. No local environment credentials are forwarded.
Real `.env`, private configuration, secrets, work directories and datasets are not
copied. Provision the remote dataset and private Operations configuration first.

## Automatic test credentials

Before any deployment, the pipeline runs `prepare-test-environment.mjs --auto-test`
on the remote host. In a fresh dedicated workDir it generates random database,
internal-service, JWT, administrator password and bootstrap credentials. Values are
consistent across services and the remote `.env`; files have mode 0600. State is
stored in ignored `simulation/test-environment.private.json`. Reruns reuse and check
these values, never rotate them automatically. Do not remove this state while
retaining databases or environment configuration.

An existing unmanaged `.env` or Secret stops execution instead of being overwritten.
Use a fresh test directory/cluster or explicitly reconcile the old environment;
changing only a workDir does not reset an existing cluster's database credentials.
This feature is exclusively for disposable `local` test environments, not production.
It does not infer registry addresses, manufacture datasets, configure remote mounts,
or rotate credentials of already deployed databases. Those non-secret environment
prerequisites still need valid machine-specific configuration.

The example uses a shared image registry. Prepare matching registry configuration
on both machines and set `MEDOL_IMAGE_SOURCE=registry` in the remote environment.
The local image steps cover generated services only: independently build/publish
the custom runtime-engine image before execution. Offline archive transfer is not
automated by this example. Existing images can be reused by removing the local
build/publish steps. No images are compiled on the remote machine.

The local pipeline report records the SSH exit status; stage JSON reports remain
under the remote `simulation/reports/` directory and can be retrieved via scp.
Terminal output is streamed through SSH. An SSH timeout/disconnect does not prove
the remote workload stopped: inspect the remote processes before retrying; there
is no automatic destructive cleanup or automatic command retry.

This integration has mocked command/validation tests, not a live remote deployment
test. Supply the host alias, remote directory and registry to perform that test.
