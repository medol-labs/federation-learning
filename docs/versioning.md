# Release Traceability

The system model lives in `.medol/source.medol`; `.medol/codegen-model.json`
contains its export metadata. Current inputs have no stable release label, so
development builds continue to use `0.0.1-SNAPSHOT`. Adding provenance does not
publish the project as `v1.0.0`.

Publish a stable baseline in MEDOL as `vX.Y.Z`, then fetch the pinned version
with `update --workspace-id WORKSPACE --version-id VERSION --locale zh-CN` and
regenerate. Model changes require a new baseline; hand-written implementation,
generator, dependency and packaging changes retaining that baseline increment
the fourth delivery part.

| Layer | Identity |
| --- | --- |
| Model | Workspace snapshot ID, release label, exact source SHA-256 |
| Generated code | `.medol/generation/<target>.json` inside each output |
| Maven/npm package | `X.Y.Z` for stable baselines |
| Images | `vX.Y.Z.N` plus image ID/registry digest and provenance labels |
| Runtime engine | Profile-prefixed image tag, for example `pytorch-vision-v1.0.0.1` |
| Documents | Independent revision, applicable model and optional delivery |

Build and package the generated applications from the system root:

```bash
node operations/images/dev/images.mjs build --version v1.0.0.0
node operations/images/dev/images.mjs package --version v1.0.0.0
```

The engine keeps its own build script and uses the root operations provenance:

```bash
cd federation-learning-runtime-engine
node scripts/build-images.mjs --version v1.0.0.0 --plugin-profile pytorch-vision
```

These stable examples require a previously published matching baseline. Drafts
cannot be relabeled as a four-part stable delivery. Use the same platform and
build options when creating an image; export/push reuse its recorded options.
Stable backend builds require the full reactor install. Dry runs do not build
images or allocate release records.

Per-application manifests are `.medol/build-manifest.json` and
`.medol/release-manifest.json`. Image archives have `.manifest.json` sidecars.
The operations bundle includes an aggregate `release-manifest.json` with its
component records. Engine profile variants have separate local release claims.

Persist `.medol/baselines/` and `.medol/releases/` with the delivery workspace.
They pin each model label to its source and each delivery to its code, options
and image IDs. Changed inputs or a mismatching image fail validation. These
local records do not replace registry immutability or signed attestations.
Offline image import does not require the original modeling source tree.
Application archives require their checksum sidecars. Import validates archive
bytes before loading and checks loaded image IDs and labels afterward. Old
unverified archives require an explicit legacy `docker load -i` command.

Documentation generation exports `source-doc-manifest.json` and
`document-manifest.json`. Documents with stale or unverified sources remain
drafts. To associate documents with actual built images, pass
`--release-manifest=path/to/release-manifest.json` to
`scripts/generate-438c-docs.mjs`; the model and built-image provenance must match.
See [438C document workflow](438c/README.md) for commands and revision rules.
