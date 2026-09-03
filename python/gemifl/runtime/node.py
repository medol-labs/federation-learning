import argparse
import json
import logging
import os
import signal
import subprocess
import sys
import time
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from urllib.parse import urlparse

log = logging.getLogger("gemifl.runtime.node")


class RuntimeState:
    def __init__(self, node_name: str, runtime_root: str):
        self.node_name = node_name
        self.runtime_root = Path(runtime_root)
        self.jobs: dict[str, dict] = {}

    def start_job(self, payload: dict) -> dict:
        job_id = str(payload.get("jobId") or "")
        if not job_id:
            raise ValueError(f"Job payload must include jobId. Received keys: {sorted(payload.keys())}")
        if job_id in self.jobs and self.jobs[job_id]["process"].poll() is None:
            raise ValueError(f"Job {job_id} is already running on {self.node_name}")

        payload["nodeName"] = payload.get("nodeName") or self.node_name
        payload.setdefault("runtimeRoot", str(self.runtime_root))

        job_dir = self.runtime_root / job_id / payload["nodeName"]
        job_dir.mkdir(parents=True, exist_ok=True)
        if payload.get("operation") == "aggregate":
            payload.setdefault("output", {}).setdefault(
                "globalModel",
                str(job_dir / "global_model.json"),
            )
        config_path = job_dir / "config.json"
        log.info(
            "Starting runtime job jobId=%s nodeName=%s operation=%s role=%s runtimeRoot=%s jobDir=%s",
            job_id,
            payload["nodeName"],
            payload.get("operation"),
            payload.get("role"),
            payload.get("runtimeRoot"),
            job_dir,
        )
        with config_path.open("w", encoding="utf-8") as f:
            json.dump(payload, f, indent=2)
        log.info("Runtime job config written jobId=%s configPath=%s", job_id, config_path)

        command = [
            sys.executable,
            "-m",
            "gemifl.runtime.executor",
            "--config",
            str(config_path),
            "--node-name",
            payload["nodeName"],
        ]
        process = subprocess.Popen(command, start_new_session=True)
        log.info("Runtime job process started jobId=%s pid=%s command=%s", job_id, process.pid, command)
        self.jobs[job_id] = {
            "process": process,
            "nodeName": payload["nodeName"],
            "configPath": str(config_path),
            "output": payload.get("output", {}),
            "startedAt": time.time(),
        }
        return self.describe_job(job_id)

    def describe_job(self, job_id: str) -> dict:
        job = self.jobs.get(job_id)
        if not job:
            raise KeyError(job_id)

        process = job["process"]
        exit_code = process.poll()
        status = "running" if exit_code is None else ("completed" if exit_code == 0 else "failed")
        node_name = job["nodeName"]
        job_dir = self.runtime_root / job_id / node_name
        status_path = job_dir / "status.txt"
        metrics_dir = job_dir / "metric"

        progress = {}
        if status_path.exists():
            try:
                progress = json.loads(status_path.read_text(encoding="utf-8"))
            except json.JSONDecodeError:
                progress = {"raw": status_path.read_text(encoding="utf-8")}

        metrics = sorted(path.name for path in metrics_dir.glob("*")) if metrics_dir.exists() else []
        description = {
            "jobId": job_id,
            "nodeName": node_name,
            "status": status,
            "exitCode": exit_code,
            "configPath": job["configPath"],
            "output": job.get("output", {}),
            "progress": progress,
            "metrics": metrics,
        }
        log.info(
            "Runtime job status jobId=%s nodeName=%s status=%s exitCode=%s output=%s metrics=%s",
            job_id,
            node_name,
            status,
            exit_code,
            job.get("output", {}),
            metrics,
        )
        return description

    def cancel_job(self, job_id: str) -> dict:
        job = self.jobs.get(job_id)
        if not job:
            raise KeyError(job_id)
        process = job["process"]
        if process.poll() is None:
            log.info("Cancelling runtime job jobId=%s pid=%s", job_id, process.pid)
            os.killpg(os.getpgid(process.pid), signal.SIGTERM)
        return self.describe_job(job_id)


class RuntimeHandler(BaseHTTPRequestHandler):
    state: RuntimeState

    def do_GET(self):
        parsed = urlparse(self.path)
        if parsed.path == "/healthz":
            self.respond(200, {"status": "ok", "nodeName": self.state.node_name})
            return
        if parsed.path.startswith("/jobs/") and "/artifacts/" in parsed.path:
            parts = parsed.path.strip("/").split("/")
            if len(parts) != 4:
                self.respond(404, {"error": "Artifact was not found"})
                return
            _, job_id, _, artifact_name = parts
            try:
                self.respond_with_job_artifact(job_id, artifact_name)
            except KeyError:
                self.respond(404, {"error": f"Artifact {artifact_name} for job {job_id} was not found"})
            return
        if parsed.path.startswith("/jobs/"):
            job_id = parsed.path.split("/", 2)[2]
            try:
                self.respond(200, self.state.describe_job(job_id))
            except KeyError:
                self.respond(404, {"error": f"Job {job_id} was not found"})
            return
        self.respond(404, {"error": "Not found"})

    def do_POST(self):
        parsed = urlparse(self.path)
        if parsed.path == "/jobs":
            try:
                payload = self.read_json()
                log.info("POST /jobs keys=%s jobId=%s", sorted(payload.keys()), payload.get("jobId"))
                self.respond(202, self.state.start_job(payload))
            except Exception as error:
                log.exception("POST /jobs failed")
                self.respond(400, {"error": str(error)})
            return
        if parsed.path.startswith("/jobs/") and parsed.path.endswith("/cancel"):
            job_id = parsed.path.split("/")[2]
            try:
                log.info("POST %s", parsed.path)
                self.respond(202, self.state.cancel_job(job_id))
            except KeyError:
                self.respond(404, {"error": f"Job {job_id} was not found"})
            except Exception as error:
                log.exception("POST %s failed", parsed.path)
                self.respond(400, {"error": str(error)})
            return
        self.respond(404, {"error": "Not found"})

    def read_json(self) -> dict:
        length = int(self.headers.get("content-length", "0"))
        if length > 0:
            raw = self.rfile.read(length)
        elif self.headers.get("transfer-encoding", "").lower() == "chunked":
            raw = self.read_chunked_body()
        else:
            raw = b""
        return json.loads(raw.decode("utf-8")) if raw else {}

    def read_chunked_body(self) -> bytes:
        chunks = []
        while True:
            size_line = self.rfile.readline().strip()
            if not size_line:
                break
            size = int(size_line.split(b";", 1)[0], 16)
            if size == 0:
                self.rfile.readline()
                break
            chunks.append(self.rfile.read(size))
            self.rfile.readline()
        return b"".join(chunks)

    def respond(self, status: int, payload: dict) -> None:
        body = json.dumps(payload, indent=2).encode("utf-8")
        self.send_response(status)
        self.send_header("content-type", "application/json")
        self.send_header("content-length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def respond_with_job_artifact(self, job_id: str, artifact_name: str) -> None:
        job = self.state.jobs.get(job_id)
        if not job:
            raise KeyError(job_id)
        artifact_path = job.get("output", {}).get(artifact_name)
        if not artifact_path:
            raise KeyError(artifact_name)
        path = Path(artifact_path).resolve()
        if not path.is_file():
            raise KeyError(artifact_name)
        body = path.read_bytes()
        self.send_response(200)
        self.send_header("content-type", "application/octet-stream")
        self.send_header("content-length", str(len(body)))
        self.send_header("content-disposition", f'attachment; filename="{path.name}"')
        self.end_headers()
        self.wfile.write(body)

    def log_message(self, format, *args):
        print(f"{self.address_string()} - {format % args}")


def env_value(primary: str, legacy: str, default: str) -> str:
    return os.getenv(primary) or os.getenv(legacy) or default


def main() -> None:
    logging.basicConfig(
        level=os.getenv("RUNTIME_ENGINE_LOG_LEVEL", "INFO").upper(),
        format="%(asctime)s %(levelname)s %(name)s - %(message)s",
    )
    parser = argparse.ArgumentParser(description="Start a runtime engine node controlled by the FL platform.")
    parser.add_argument("--host", default=env_value("RUNTIME_ENGINE_HOST", "GEMIFL_RUNTIME_HOST", "0.0.0.0"))
    parser.add_argument("--port", type=int, default=int(env_value("RUNTIME_ENGINE_PORT", "GEMIFL_RUNTIME_PORT", "8080")))
    parser.add_argument("--node-name", default=env_value("RUNTIME_ENGINE_NODE_NAME", "GEMIFL_NODE_NAME", "runtime-node"))
    parser.add_argument("--runtime-root", default=env_value("RUNTIME_ENGINE_ROOT", "GEMIFL_RUNTIME_ROOT", "./tmp/runtime-engine"))
    args = parser.parse_args()

    RuntimeHandler.state = RuntimeState(args.node_name, args.runtime_root)
    server = ThreadingHTTPServer((args.host, args.port), RuntimeHandler)
    log.info(
        "Runtime engine node listening nodeName=%s host=%s port=%s runtimeRoot=%s",
        args.node_name,
        args.host,
        args.port,
        args.runtime_root,
    )
    server.serve_forever()


if __name__ == "__main__":
    main()
