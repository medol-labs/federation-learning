import sys
import time
import torch
import logging
import torch.nn as nn
from transformers import AdamW
from gemifl.utils.base_modules import Tokenizer, DataHandler, ModelLoad, Federation, InfoRecord, ConfigParser
logging.basicConfig(level=logging.INFO, stream=sys.stdout)


class ClinicalBertClient:
    def __init__(self, config) -> None:
        self.params_parser = ConfigParser(config)

        # initialize clinical bert params
        self.params_parser.set_clinical_bert()
        self.tokenizer = Tokenizer(self.params_parser.token_path)
        self.model = ModelLoad(self.params_parser.model_path,
                               self.params_parser.num_labels).model

        # set data-loader for bert task
        self.params_parser.set_data_source()
        self.data_loader = DataHandler(
            self.params_parser.data_source, self.tokenizer, batch_size=16)

        # set grpc client and roles for federation
        self.fed_client = Federation(
            self.params_parser.my_name, self.params_parser.roles, self.params_parser.node_config)

        # set save status recorder
        self.info_recoder = InfoRecord(
            taks_id=self.params_parser.task_id, role_name=self.params_parser.my_name)

    def train(self, data_loader, type="train", device="cuda"):
        self.model.to(device)
        if type == "train":
            criterion = nn.CrossEntropyLoss()
            optimizer = AdamW(self.model.parameters(), lr=1e-5, eps=1e-8)

            self.model.train()
            for step, tmp_batch in enumerate(data_loader):
                tmp_ids = tmp_batch[0].to(device)
                tmp_mask = tmp_batch[1].to(device)
                tmp_label = tmp_batch[2].to(device)

                # Clear any previously calculated gradients
                self.model.zero_grad()

                outputs = self.model(tmp_ids,
                                     token_type_ids=None,
                                     attention_mask=tmp_mask,
                                     labels=tmp_label)

                loss = outputs[0]
                loss.backward()

                optimizer.step()

                # break

        elif type == "valid":
            ncorrect = 0
            nsamples = 0
            self.model.eval()
            with torch.no_grad():
                for step, tmp_batch in enumerate(data_loader):
                    tmp_ids = tmp_batch[0].to(device)
                    tmp_mask = tmp_batch[1].to(device)
                    tmp_label = tmp_batch[2].to(device)

                    outputs = self.model(tmp_ids, tmp_mask)
                    y_pred = torch.argmax(outputs.logits, axis=1)

                    ncorrect += sum(y_pred == tmp_label)
                    nsamples += len(tmp_ids)

                    # break

                acc = float(ncorrect) / float(nsamples)

                print('| Valid Accuracy: ', float(ncorrect) / float(nsamples))
                return acc
        
        else:
            raise ValueError("It's not implemented!")

    def run(self):
        start_time = time.time()
        epochs = self.params_parser.model_parameter.get("epochs")

        train_loader = self.data_loader.train_loader
        test_loader = self.data_loader.test_loader

        for epoch in range(epochs):

            # local model training
            self.train(train_loader)

            # model averaging
            weight_key = f"net_params_{epoch}"
            averaging_net_params = self.fed_client.average_weights(key=weight_key, val=self.model.state_dict())

            logging.info(
                f"current epoch {epoch} finished.")

            # update local model weights
            self.model.load_state_dict(averaging_net_params)

            # valid on local data
            acc = self.train(test_loader, type="valid")

            status = {
                "time_left":
                str((time.time() - start_time) * (epochs - epoch - 1) /
                    (epoch + 1)) + "s",
                "is_converged":
                False,
                    "progress_rate":
                        float(epoch + 1) / epochs
            }
            self.info_recoder.record_status(status)
            self.info_recoder.record_metrics({"acc": acc, "n_iter": epoch})
         