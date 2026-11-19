"""Evaluate an immutable binary logistic regression artifact on a fixed CSV holdout."""

import base64
import csv
import hashlib
import io
import json
import math
import os
from pathlib import Path


def evaluate(request):
    if request["modelPlugin"] not in ("linear.LogisticRegression", "SKLEARN_LOGISTIC_REGRESSION"):
        raise ValueError("Evaluation is implemented only for binary logistic regression")
    raw = base64.b64decode(request["modelContent"], validate=True)
    digest = hashlib.sha256(raw).hexdigest()
    if digest != request["modelArtifactDigest"].lower().removeprefix("sha256:"):
        raise ValueError("Aggregated model SHA-256 mismatch")
    model = json.loads(raw)
    weights = model["weights"]
    bias = model["bias"]
    if not weights or not all(math.isfinite(v) for v in [bias, *weights]):
        raise ValueError("Invalid logistic regression weights")

    # A custom holdout must declare the ordered features used by training.
    configured = os.environ.get("RUNTIME_ENGINE_EVALUATION_CONFIG")
    if not configured:
        raise ValueError("RUNTIME_ENGINE_EVALUATION_CONFIG must identify a fixed validation manifest")
    config_path = Path(configured)
    config = json.loads(config_path.read_text())
    if config.get("featureSchemaId") and request.get("featureSchemaId") != config["featureSchemaId"]:
        raise ValueError("Validation dataset belongs to a different feature schema")
    data = (config_path.parent / config["file"]).read_bytes()
    data_digest = hashlib.sha256(data).hexdigest()
    if data_digest != config["sha256"] or not config["version"].strip():
        raise ValueError("Invalid validation dataset version or SHA-256")
    features, label = config["features"], config["label"]
    rows = csv.DictReader(io.StringIO(data.decode("utf-8-sig")))
    if (len(features) != len(weights) or len(set(features + [label])) != len(features) + 1
            or not set(features + [label]).issubset(rows.fieldnames or [])):
        raise ValueError("Validation schema does not match model")
    counts = [[0, 0], [0, 0]]
    for row in rows:
        score = bias + sum(w * float(row[f]) for w, f in zip(weights, features))
        if row[label] not in ("0", "1") or not math.isfinite(score):
            raise ValueError("Invalid validation row")
        counts[int(row[label])][int(score >= 0)] += 1
    (tn, fp), (fn, tp) = counts
    n = tn + fp + fn + tp
    if not n:
        raise ValueError("Empty validation dataset")
    return dict(modelArtifactDigest=digest, validationDatasetVersion=config["version"],
                validationDatasetDigest=data_digest, evaluatedSampleCount=n,
                truePositiveCount=tp, trueNegativeCount=tn, falsePositiveCount=fp,
                falseNegativeCount=fn, globalAccuracy=(tp + tn) / n)
