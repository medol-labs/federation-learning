# Native single-node deployment

This entry combines all applications and infrastructure once. Do not also apply the site entries.
Use one Linux K3s server with its agent enabled and a default StorageClass. On macOS use a Linux VM.
The helper does not install K3s, create credentials, taint nodes or configure business-specific schedulers.
Import version/architecture-matched system, application and engine archives into K3s containerd.
Set SECRETS_FILE to a real Secret manifest; keep it outside Git. EXTRA_COMPONENT accepts a reviewed Kustomize component directory.
Run: bash operations/k3s/scripts/k3s-single-node.sh dev render|check|import|apply
Access NodePort services through the Linux node address. Review resource budgets, storage sizes and node-local data paths.
