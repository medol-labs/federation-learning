# System Notification Catalog Cases

Project-owned manual cases: `system-notification.json`. They are drafts pending
review, not model-generated cases and not evidence of a successful live run.
The executable is the shared HTTP case runner, configured by
`simulation/pipelines/notification-http-executor.json`. There is no notification-specific JS adapter.
No business behavior is added to the generator or generated context code.

## Coverage

| Case | Expected behavior |
| --- | --- |
| system-notification-publish-and-read | Publish an unread error, query catalog and summary, acknowledge one, verify read timestamp and zero unread count |
| system-notification-filter-and-pagination | Recipient/category/severity/state criteria, page size one with total count two and second page |
| system-notification-mark-all-recipient-scope | Acknowledge two notifications for one recipient; both catalog rows and summary agree; another recipient remains unread |

Each case creates fresh UUIDs and synthetic recipient strings. No real user's
notifications are acknowledged. These are privileged administrative API tests;
recipient filtering is not proof of access-control isolation between users.
The deployed model currently accepts recipientUserId as a string without a user
existence prerequisite. If this changes, add reviewed user fixtures first.
Fixtures are retained as evidence; no notification deletion API is modeled.
Commands are never retried. Queries poll expected projections for up to 30 seconds,
within the 180-second case limit. Transport/authentication failures fail immediately.
This suite checks API projections, not visual notification/toast behavior.

## Run Against an Existing Deployment

From the project root, review and approve into a new snapshot, then select:

```sh
node simulation/src/cases.mjs validate simulation/cases/manual/system-notification.json
node simulation/src/cases.mjs approve simulation/cases/manual/system-notification.json simulation/cases/approved/system-notification-v1.json <reviewer>
node simulation/src/cases.mjs select simulation/cases/approved/system-notification-v1.json simulation/cases/runs/notifications-001.json simulation/pipelines/case-selection-system-notification.json
```

Use the existing ready deployment manifest containing `environment`,
`deploymentId`, `status: "ready"` and `endpoints.support` (the support API base,
including its gateway prefix). Do not supply the infrastructure-only environment
report. No cluster preparation, image build or training-data generation is needed.

The notification data plan checks authenticated catalog and summary queries and
does not create business fixtures. Each case creates its own isolated data during
execution. No training baseline is required. The generic preparation command
binds its ready manifest to the selected case plan and deployment; do not reuse
a training data manifest.

```sh
export FL_API_TOKEN='<authorized API token>'
node simulation/src/prepare-data.mjs case --plan simulation/data/system-notification-case-plan.json --environment <deployment.json> --cases simulation/cases/runs/notifications-001.json --adapter simulation/pipelines/notification-http-executor.json --output simulation/reports/notification-data-001.json
node simulation/src/execute-cases.mjs simulation/cases/runs/notifications-001.json <deployment.json> simulation/reports/notification-data-001.json simulation/pipelines/notification-http-executor.json simulation/reports/notifications-001.json
```

The adapter reads FL_API_TOKEN from the process environment, not automatically
from `.env`. Never commit the token. Use a fresh report/selection path per run.
For offline execution prefix Node commands with `sh run-simulation.sh exec` and
provide the token through the tool runner's supported environment configuration.

## Known Gap to Expose

The current generated `SystemNotificationCatalogReadModelProjector` skips
`AllSystemNotificationsReadEvent` because the event has a recipient key rather
than a notification key. The summary does clear its unread count. Unless a
deployment supplies an override, the mark-all case should fail on catalog rows
remaining Unread. Keep this assertion; do not mark the case passed merely because
the command succeeded or the summary became zero. Fixing that projection is a
separate model/generator task, not a hand edit under `context`.
