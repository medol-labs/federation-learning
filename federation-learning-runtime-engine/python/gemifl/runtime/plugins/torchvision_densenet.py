from __future__ import annotations

import logging
import time
from pathlib import Path

import torch
from torch import nn, optim
from torch.utils.data import DataLoader
from torchvision import datasets, models, transforms

from gemifl.runtime.plugins.io import default_artifact_path, read_json, write_json


log = logging.getLogger("gemifl.runtime.plugins.torchvision_densenet")


class TorchvisionDenseNet121ClassifierPlugin:
    code = "PYTORCH_TORCHVISION_DENSENET121_CLASSIFIER"
    aliases = ("TORCHVISION_DENSENET121", "DENSENET121", "PYTORCH_DENSENET121_CLASSIFIER")
    supported_formats = ("PYTORCH_STATE_DICT",)
    supported_tasks = ("IMAGE_CLASSIFICATION", "MEDICAL_IMAGE_CLASSIFICATION")

    def train(self, config: dict) -> None:
        input_config = config.get("input", {})
        model_parameter = config.get("modelParameter", {})
        output = config.get("output", {})

        dataset_config = input_config.get("dataset") or {}
        train_dir = dataset_config.get("train") or dataset_config.get("trainPath") or dataset_config.get("path")
        val_dir = dataset_config.get("val") or dataset_config.get("validation") or dataset_config.get("validationPath") or train_dir
        if not train_dir:
            raise ValueError("Torchvision DenseNet121 requires input.dataset.train or input.dataset.path")

        image_size = int(model_parameter.get("imageSize", 224))
        train_batch_size = int(model_parameter.get("trainBatchSize") or model_parameter.get("train_batch_size") or 8)
        valid_batch_size = int(model_parameter.get("validBatchSize") or model_parameter.get("valid_batch_size") or train_batch_size)
        epochs = int(model_parameter.get("epochs") or model_parameter.get("epoch") or 1)
        learning_rate = float(model_parameter.get("learningRate") or model_parameter.get("lr") or 0.001)

        train_dataset = datasets.ImageFolder(train_dir, transform=self._transform(image_size, training=True))
        val_dataset = datasets.ImageFolder(val_dir, transform=self._transform(image_size, training=False)) if val_dir else None
        class_count = int(model_parameter.get("numClasses") or model_parameter.get("num_classes") or len(train_dataset.classes))
        if class_count <= 1:
            raise ValueError("Torchvision DenseNet121 requires at least two classes")

        train_loader = DataLoader(train_dataset, batch_size=train_batch_size, shuffle=True)
        val_loader = DataLoader(val_dataset, batch_size=valid_batch_size, shuffle=False) if val_dataset else None
        log.info(
            "Loaded torchvision DenseNet121 dataset jobId=%s trainDir=%s validationDir=%s "
            "classes=%s trainSamples=%s validationSamples=%s trainBatches=%s validationBatches=%s imageSize=%s",
            config.get("jobId"),
            train_dir,
            val_dir,
            train_dataset.classes,
            len(train_dataset),
            len(val_dataset) if val_dataset else 0,
            len(train_loader),
            len(val_loader) if val_loader else 0,
            image_size,
        )
        device = self._device(model_parameter)
        model = self._create_model(model_parameter, class_count)
        model.to(device)
        self._load_initial_model(model, input_config.get("globalModel"), device)
        trainable_parameter_count = sum(parameter.numel() for parameter in model.parameters() if parameter.requires_grad)
        log.info(
            "Initialized torchvision DenseNet121 model jobId=%s trainableParameters=%s device=%s",
            config.get("jobId"),
            trainable_parameter_count,
            device,
        )

        criterion = nn.CrossEntropyLoss()
        optimizer = optim.Adam(model.parameters(), lr=learning_rate)
        log.info(
            "Training torchvision DenseNet121 jobId=%s classes=%s samples=%s epochs=%s learningRate=%s device=%s",
            config.get("jobId"),
            class_count,
            len(train_dataset),
            epochs,
            learning_rate,
            device,
        )

        model.train()
        last_loss = 0.0
        for epoch_index in range(epochs):
            epoch_started_at = time.monotonic()
            epoch_loss = 0.0
            processed_samples = 0
            log.info(
                "DenseNet121 epoch started jobId=%s epoch=%s/%s batches=%s learningRate=%s",
                config.get("jobId"),
                epoch_index + 1,
                epochs,
                len(train_loader),
                optimizer.param_groups[0]["lr"],
            )
            for batch_index, (inputs, labels) in enumerate(train_loader, start=1):
                inputs = inputs.to(device)
                labels = labels.to(device)
                optimizer.zero_grad()
                loss = criterion(model(inputs), labels)
                loss.backward()
                optimizer.step()
                last_loss = float(loss.item())
                batch_samples = int(labels.size(0))
                processed_samples += batch_samples
                epoch_loss += last_loss * batch_samples
                log.debug(
                    "DenseNet121 batch completed jobId=%s epoch=%s/%s batch=%s/%s "
                    "processedSamples=%s loss=%.6f",
                    config.get("jobId"),
                    epoch_index + 1,
                    epochs,
                    batch_index,
                    len(train_loader),
                    processed_samples,
                    last_loss,
                )
            average_epoch_loss = epoch_loss / processed_samples if processed_samples else 0.0
            log.info(
                "DenseNet121 epoch completed jobId=%s epoch=%s/%s samples=%s averageLoss=%.6f durationSeconds=%.2f",
                config.get("jobId"),
                epoch_index + 1,
                epochs,
                processed_samples,
                average_epoch_loss,
                time.monotonic() - epoch_started_at,
            )

        metrics = {
            "samples": len(train_dataset),
            "classes": train_dataset.classes,
            "loss": last_loss,
        }
        if val_loader:
            log.info(
                "DenseNet121 validation started jobId=%s samples=%s batches=%s",
                config.get("jobId"),
                len(val_dataset),
                len(val_loader),
            )
            metrics.update(self._evaluate(model, val_loader, device))
            log.info(
                "DenseNet121 validation completed jobId=%s accuracy=%s",
                config.get("jobId"),
                metrics.get("validationAccuracy"),
            )

        weight_artifact = output.get("weightArtifact") or default_artifact_path(config, "model_state_dict.pt")
        model_artifact = output.get("modelArtifact") or default_artifact_path(config, "model.pt")
        local_update = output.get("localUpdate") or default_artifact_path(config, "local_update.json")
        metrics_path = output.get("metrics") or default_artifact_path(config, "metrics.json")

        log.info(
            "Saving torchvision DenseNet121 artifacts jobId=%s modelArtifact=%s weightArtifact=%s "
            "localUpdate=%s metrics=%s",
            config.get("jobId"),
            model_artifact,
            weight_artifact,
            local_update,
            metrics_path,
        )
        self._save_state_dict(model, weight_artifact)
        self._save_model(model, model_artifact)
        write_json(
            local_update,
            {
                "modelPlugin": self.code,
                "weightArtifact": weight_artifact,
                "modelArtifact": model_artifact,
                "format": "PYTORCH_STATE_DICT",
                "train_size": len(train_dataset),
                "classes": train_dataset.classes,
            },
        )
        write_json(metrics_path, metrics)
        log.info(
            "Torchvision DenseNet121 completed jobId=%s localUpdate=%s weightArtifact=%s metrics=%s",
            config.get("jobId"),
            local_update,
            weight_artifact,
            metrics_path,
        )

    def _create_model(self, model_parameter: dict, class_count: int):
        weights_name = model_parameter.get("pretrainedWeights")
        weights = None
        if weights_name:
            if weights_name in {"DEFAULT", "IMAGENET1K_V1"}:
                weights = models.DenseNet121_Weights.DEFAULT
            else:
                raise ValueError(f"Unsupported DenseNet121 pretrainedWeights: {weights_name}")
        model = models.densenet121(weights=weights)
        model.classifier = nn.Linear(model.classifier.in_features, class_count)
        return model

    def _load_initial_model(self, model, global_model, device) -> None:
        artifact_path = self._artifact_path(global_model)
        if not artifact_path:
            return
        path = Path(artifact_path)
        if not path.exists():
            raise ValueError(f"Initial model artifact does not exist: {artifact_path}")
        if path.suffix.lower() == ".json":
            payload = read_json(path)
            artifact_path = self._artifact_path(payload.get("weightArtifact") or payload.get("modelArtifact"))
            if not artifact_path:
                raise ValueError("Initial model JSON must contain weightArtifact or modelArtifact")
            path = Path(artifact_path)
        payload = torch.load(path, map_location=device)
        state_dict = payload.get("state_dict") if isinstance(payload, dict) and "state_dict" in payload else payload
        model.load_state_dict(state_dict)
        log.info("Loaded initial torchvision DenseNet121 artifact=%s", path)

    def _artifact_path(self, artifact) -> str | None:
        if isinstance(artifact, dict):
            return self._artifact_path(artifact.get("weightArtifact") or artifact.get("modelArtifact"))
        if not isinstance(artifact, str) or not artifact:
            return None
        if artifact.startswith("builtin://"):
            log.info("Skipping built-in DenseNet121 initial model reference artifact=%s", artifact)
            return None
        if artifact.startswith("file://"):
            return artifact.removeprefix("file://")
        return artifact

    def _transform(self, image_size: int, training: bool):
        steps = []
        if training:
            steps.append(transforms.RandomHorizontalFlip())
        steps.extend(
            [
                transforms.Resize((image_size, image_size)),
                transforms.ToTensor(),
                transforms.Normalize(
                    mean=(0.485, 0.456, 0.406),
                    std=(0.229, 0.224, 0.225),
                ),
            ],
        )
        return transforms.Compose(steps)

    def _evaluate(self, model, data_loader, device) -> dict:
        model.eval()
        correct = 0
        total = 0
        with torch.no_grad():
            for inputs, labels in data_loader:
                inputs = inputs.to(device)
                labels = labels.to(device)
                predictions = model(inputs).argmax(dim=1)
                correct += int((predictions == labels).sum().item())
                total += int(labels.size(0))
        model.train()
        return {
            "validationSamples": total,
            "validationAccuracy": float(correct / total) if total else None,
        }

    def _device(self, model_parameter: dict):
        requested_device = str(model_parameter.get("device") or "cpu").lower()
        if requested_device != "cpu" and torch.cuda.is_available():
            return torch.device(requested_device)
        return torch.device("cpu")

    def _save_state_dict(self, model, path: str) -> None:
        output_path = Path(path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        torch.save(model.state_dict(), output_path)

    def _save_model(self, model, path: str) -> None:
        output_path = Path(path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        torch.save(model, output_path)
