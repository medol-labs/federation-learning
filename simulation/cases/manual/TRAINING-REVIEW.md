# Two-Participant CSV Acceptance Review

Reviewed on 2026-11-16 for steps 4 (case design) and 5 (data preparation).

## Scope

`../approved/training.json` covers one baseline end-to-end case: two distinct runtime
identities contribute to a one-round global training job using the CSV scenario.
It is not a complete system test suite. Approval means the case has been reviewed
against the current project executor; it does not mean the test has passed.

The executor supports SubmitTraining followed by VerifyTraining. Its verification
checks job completion, the exact selected runtime set and quorum, accepted updates,
each participant's submitted update, an aggregated model record, nonempty artifact
download and SHA-256 equality. Tests for contribution-set and artifact checks pass.
The case additionally asserts that the submission snapshot has two participants.

## Fixture

Use `extensions/fixtures/test.csv`: 32 synthetic rows with distinct sample IDs,
three numeric model features and a balanced binary `readmission_risk` label.
SHA-256: `b1c3c46512ce01f2c3b8ba7f614c642e6a33546168d309914a6ff77d1b93736e`.
The same fixture is intentionally copied to both isolated participant nodes for
a deterministic workflow smoke test. This does not simulate independent clinical
populations, non-IID data or privacy guarantees; it contains no real patient data.

Host input: `volumes/datasets/test.csv` under the remote project directory.
Both K3d worker nodes and Agent containers must expose `/workspace/datasets/test.csv`.
Copies made into node containers survive application restarts, not cluster deletion;
repeat fixture provisioning after recreating the nodes.

The existing configuration is reused with its original training-run key:
`simulation-1794796914348`. Expected configuration: maxRounds=1,
minimumNodesPerRound=2, aggregationAlgorithm=FED_AVG_JSON, sklearn runtime profile.
The configured secure aggregation path is exercised by a later test run, but this
case does not assert cryptographic security or production key management.

## Exclusions And Gates

- No training is submitted during case review or baseline data preparation.
- Model accuracy, convergence, adversarial inputs, offline participants, retries,
  authorization boundaries and 20-participant capacity need additional cases.
- Prepared data must have actual Agent capability and platform metadata readiness;
  registration alone is insufficient. Retain IDs in the ready-data manifest.
- Step 6 must revalidate deployment/data identity and executable case approval.
- HTTP artifact reachability from the test worker is required; an internal-only
  artifact URL must not be silently accepted or rewritten to pass verification.
- Readiness does not promise the next test will pass. Runtime failures belong in
  its test report rather than being hidden by reseeding or direct database updates.

The retained approval was recorded by Codex, not by the user. The editable source
is training.json in this directory. Re-review changes before creating a new snapshot.
