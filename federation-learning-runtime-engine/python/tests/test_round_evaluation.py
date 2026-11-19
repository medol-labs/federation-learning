import base64
import hashlib
import json
import unittest
from pathlib import Path
from unittest.mock import patch
from http.server import ThreadingHTTPServer
from threading import Thread
from urllib.request import Request, urlopen

from gemifl.runtime.evaluation import evaluate
from gemifl.runtime.node import RuntimeHandler


class RoundEvaluationTest(unittest.TestCase):
    def setUp(self):
        manifest = Path(__file__).resolve().parents[1] / "gemifl/runtime/holdout.json"
        env = patch.dict("os.environ", RUNTIME_ENGINE_EVALUATION_CONFIG=str(manifest))
        env.start()
        self.addCleanup(env.stop)

    def request(self, bias=-55):
        raw = json.dumps({"weights": [1, 0, 0], "bias": bias}).encode()
        return {"modelContent": base64.b64encode(raw).decode(),
                "modelPlugin": "SKLEARN_LOGISTIC_REGRESSION",
                "modelArtifactDigest": hashlib.sha256(raw).hexdigest()}

    def test_rounds_use_same_holdout_but_different_predictions(self):
        first = evaluate(self.request())
        second = evaluate(self.request(-58))
        self.assertEqual((first["truePositiveCount"], first["trueNegativeCount"],
                          first["falsePositiveCount"], first["falseNegativeCount"]), (4, 3, 1, 0))
        self.assertEqual(first["globalAccuracy"], 7 / 8)
        self.assertEqual(second["globalAccuracy"], 1)
        self.assertEqual(first["validationDatasetDigest"], second["validationDatasetDigest"])
        self.assertNotEqual(first["modelArtifactDigest"], second["modelArtifactDigest"])

    def test_digest_mismatch_fails_closed(self):
        with self.assertRaisesRegex(ValueError, "SHA-256"):
            evaluate(dict(self.request(), modelArtifactDigest="0" * 64))

    def test_http_evaluation_returns_verified_metrics(self):
        with ThreadingHTTPServer(("127.0.0.1", 0), RuntimeHandler) as server:
            thread = Thread(target=server.handle_request)
            thread.start()
            request = Request(f"http://127.0.0.1:{server.server_port}/evaluations",
                              data=json.dumps(self.request()).encode(),
                              headers={"Content-Type": "application/json"})
            with urlopen(request, timeout=5) as response:
                result = json.load(response)
            thread.join(timeout=5)
            self.assertEqual(result["evaluatedSampleCount"], 8)
            self.assertEqual(result["globalAccuracy"], 7 / 8)
