import os
import sys
import pickle
import logging
import torch
from fairseq.models.roberta import RobertaModel
from gemifl.network.grpc_client import Client
from gemifl.utils.metrics_recorder import MetricRecord
from torch.nn import functional

logging.basicConfig(level=logging.INFO, stream=sys.stdout)


def average_weights(bert_model,
                    my_node_name,
                    selected_node,
                    client,
                    epoch,
                    sorted_nodes,
                    average_keys=[]):
    model = bert_model["model"]

    # total_model_weights = model.state_dict()

    if not average_keys:
        local_model_weights = {
            list(model.keys())[0]: model[list(model.keys())[0]]
        }
    else:
        local_model_weights = {}
        for tmp_key in average_keys:
            local_model_weights[tmp_key] = model[tmp_key]

    # send local model weights to selected node
    if my_node_name != selected_node:
        client.send(target_role=selected_node,
                    key=my_node_name + "_net_params_" + str(epoch),
                    val=local_model_weights)
        averaging_net_params = client.get(target_role=selected_node,
                                          key=selected_node + "_net_params_" +
                                          str(epoch))
    else:
        # averaging model weights from receiving parameters
        averaging_net_params = local_model_weights
        num = 1
        for tmp_node in sorted_nodes:
            if tmp_node != my_node_name:
                tmp_val = client.get(target_role=tmp_node,
                                     key=tmp_node + "_net_params_" + str(epoch))

                # increase current model parameters
                for k in averaging_net_params.keys():
                    averaging_net_params[k] = torch.add(averaging_net_params[k],
                                                        tmp_val[k])

                num += 1

        # averaging model parameters
        for k in averaging_net_params.keys():
            averaging_net_params[k] = torch.divide(averaging_net_params[k], num)

        # send averaging model parameters to other neighbors
        for tmp_node in sorted_nodes:
            if tmp_node != my_node_name:
                client.send(target_role=tmp_node,
                            key=my_node_name + "_net_params_" + str(epoch),
                            val=averaging_net_params)
    for key, val in averaging_net_params.items():
        model[key] = val

    # model.load_state_dict(model)

    bert_model["model"] = model
    return bert_model


class BertClient:

    def __init__(self, config) -> None:
        self.config = config
        self.nodes = self.config["nodes"]
        self.nodes_parameter = self.config["node_parameter"]
        self.roles = self.config["roles"]
        self.model_parameter = self.config["model_parameter"]

    def run(self):
        os.environ['NUMEXPR_MAX_THREADS'] = '40'
        #os.environ['CUDA_LAUNCH_BLOCKING'] = "1"
        torch.backends.cudnn.enable = True
        my_node_name = list(self.nodes_parameter.keys())[0]
        sorted_nodes = sorted(list(self.nodes.keys()))

        train_dir = self.nodes_parameter[my_node_name]["data_source"][
            "train_dir"]
        save_checkpoints = self.nodes_parameter[my_node_name]["data_source"][
            "save_checkpoints"]
        restore_file = self.nodes_parameter[my_node_name]["data_source"][
            "restore_file"]
        log_dir = self.nodes_parameter[my_node_name]["data_source"]["log_dir"]

        # choose one node for aggregation
        selected_node = sorted_nodes[0]
        other_nodes = [key for key in self.nodes.keys() if key != my_node_name]
        logging.info(
            f"The current role is: {my_node_name} and other nodes {other_nodes}"
        )

        TOKENS_PER_SAMPLE = self.model_parameter["TOKENS_PER_SAMPLE"]
        WARMUP_UPDATES = self.model_parameter["WARMUP_UPDATES"]
        TOTAL_UPDATES = self.model_parameter["TOTAL_UPDATES"]
        MAX_SENTENCES = self.model_parameter["MAX_SENTENCES"]
        UPDATE_FREQ = self.model_parameter["UPDATE_FREQ"]
        ROOT_DIR = self.model_parameter["ROOT_DIR"]
        AVG_PERIOD = self.model_parameter["AVG_PERIOD"]
        MAX_EPOCH = self.model_parameter["MAX_EPOCH"]
        PEAK_LR = self.model_parameter["PEAK_LR"]

        data_bin_dir = os.path.join(ROOT_DIR, train_dir)
        train_log_dir = os.path.join(ROOT_DIR, log_dir)
        save_checkpoints_dir = os.path.join(ROOT_DIR, save_checkpoints)
        grpc_client = Client(node_config=self.nodes, my_name=my_node_name)

        cpu_flag = False
        if my_node_name == "Alice":
            cpu_flag = True

        # training process
        train_cmd = f"fairseq-train --max-update {TOTAL_UPDATES} " \
                    f"--task masked_lm --criterion masked_lm " \
                    f"--arch roberta_base --sample-break-mode complete --tokens-per-sample {TOKENS_PER_SAMPLE} " \
                    f"--optimizer adam --adam-betas 0.9,0.98 --adam-eps 1e-6 --clip-norm 0.0 " \
                    f"--lr-scheduler polynomial_decay --lr {PEAK_LR} --warmup-updates {WARMUP_UPDATES} --total-num-update {TOTAL_UPDATES} " \
                    f"--dropout 0.1 --attention-dropout 0.1 --weight-decay 0.01 " \
                    f"--batch-size {MAX_SENTENCES} --update-freq {UPDATE_FREQ} " \
                    f"--log-format simple --log-interval 1 --tensorboard-logdir {train_log_dir} " \
                    f"--save-interval 1 --save-dir {save_checkpoints_dir} --fp16 {data_bin_dir} " \
                    "--max-epoch {current_epochs} " \
                    "--restore-file {restore_file_dir} "

        if not os.path.exists(os.path.join(ROOT_DIR, restore_file)):
            os.makedirs(os.path.join(ROOT_DIR, restore_file))

        for epoch in range(0, MAX_EPOCH, AVG_PERIOD):
            # start training robert
            restore_file_dir = os.path.join(
                ROOT_DIR, restore_file) + f"checkpoint_avg_{epoch}.pt"
            pre_train = train_cmd.format(current_epochs=epoch + AVG_PERIOD,
                                         restore_file_dir=restore_file_dir)
            print("pre_train: ", pre_train)

            assert os.system(pre_train) == 0

            # averaging model parameters and save
            bert_model = torch.load(os.path.join(save_checkpoints_dir,
                                                 "checkpoint_last.pt"),
                                    map_location=torch.device("cpu"))

            bert_model = average_weights(bert_model=bert_model,
                                         my_node_name=my_node_name,
                                         selected_node=selected_node,
                                         client=grpc_client,
                                         epoch=epoch,
                                         sorted_nodes=sorted_nodes)

            torch.save(bert_model, restore_file_dir)

        MAX_EPOCH = 500
        AVG_PERIOD = 50

        for epoch in range(300, MAX_EPOCH, AVG_PERIOD):
            # train and average embeddings
            restore_file_dir = os.path.join(
                ROOT_DIR, restore_file) + f"checkpoint_avg_{epoch}.pt"

            emd_train = train_cmd.format(current_epochs=epoch + AVG_PERIOD,
                                         restore_file_dir=restore_file_dir)
            print("emd_train: ", emd_train)

            assert os.system(emd_train) == 0

            # averaging model parameters and save
            bert_model = torch.load(os.path.join(save_checkpoints_dir,
                                                 "checkpoint_last.pt"),
                                    map_location=torch.device("cpu"))

            bert_model = average_weights(
                bert_model=bert_model,
                my_node_name=my_node_name,
                selected_node=selected_node,
                client=grpc_client,
                epoch=epoch,
                sorted_nodes=sorted_nodes,
                average_keys=[
                    # "encoder.sentence_encoder.embed_tokens.weight",
                    # "encoder.sentence_encoder.embed_positions.weight"
                ])

            torch.save(bert_model, restore_file_dir)

        for epoch in range(450, MAX_EPOCH, AVG_PERIOD):
            # train and average head embeddings
            restore_file_dir = os.path.join(
                ROOT_DIR, restore_file) + f"checkpoint_avg_{epoch}.pt"

            head_emd_train = train_cmd.format(current_epochs=epoch + AVG_PERIOD,
                                              restore_file_dir=restore_file_dir)
            # average bert parameters
            assert os.system(head_emd_train) == 0

            # averaging model parameters and save
            bert_model = torch.load(os.path.join(save_checkpoints_dir,
                                                 "checkpoint_last.pt"),
                                    map_location=torch.device("cpu"))

            bert_model = average_weights(
                bert_model=bert_model,
                my_node_name=my_node_name,
                selected_node=selected_node,
                client=grpc_client,
                epoch=epoch,
                sorted_nodes=sorted_nodes,
                average_keys=[
                    # "encoder.sentence_encoder.embed_tokens.weight",
                    # "encoder.sentence_encoder.embed_positions.weight",
                    # "encoder.lm_head.weight", "encoder.lm_head.bias",
                    # "encoder.lm_head.dense.weight",
                    # "encoder.lm_head.dense.bias",
                    # "encoder.lm_head.layer_norm.weight",
                    # "encoder.lm_head.layer_norm.bias"
                ])

            torch.save(bert_model, restore_file_dir)

        for epoch in range(450, MAX_EPOCH, AVG_PERIOD):
            # train and average head embeddings
            restore_file_dir = os.path.join(
                ROOT_DIR, restore_file) + f"checkpoint_avg_{epoch}.pt"

        # fine-tuning process
        fine_tune_params = self.nodes_parameter[my_node_name]["fine_tune"]
        TOTAL_NUM_UPDATES = fine_tune_params["TOTAL_NUM_UPDATES"]
        WARMUP_UPDATES = fine_tune_params["WARMUP_UPDATES"]
        LR = fine_tune_params["LR"]
        NUM_CLASSES = fine_tune_params["NUM_CLASSES"]
        MAX_SENTENCES = fine_tune_params["MAX_SENTENCES"]
        HEAD_NAME = fine_tune_params["HEAD_NAME"]
        bin_dir = fine_tune_params["bin_dir"]
        # robert_path = fine_tune_params["robert_path"]
        robert_path = restore_file_dir
        torch.set_default_dtype(torch.float64)

        tune_cmd = f"fairseq-train {bin_dir} --restore-file {robert_path} --max-positions 512 " \
                   f"--batch-size {MAX_SENTENCES} --max-tokens 4400 --task sentence_prediction " \
                   f"--reset-optimizer --reset-dataloader --reset-meters --required-batch-size-multiple 1 " \
                   f"--init-token 0 --separator-token 2 --arch roberta_base " \
                   f"--criterion sentence_prediction --classification-head-name {HEAD_NAME} " \
                   f"--num-classes {NUM_CLASSES} --dropout 0.1 --attention-dropout 0.1 " \
                   f"--weight-decay 0.1 --optimizer adam --adam-betas 0.9,0.98 --adam-eps 1e-06 " \
                   f"--clip-norm 0.0 --lr-scheduler polynomial_decay --lr {LR} --total-num-update {TOTAL_NUM_UPDATES} " \
                   f"--warmup-updates {WARMUP_UPDATES} --fp16 --fp16-init-scale 4 --threshold-loss-scale 1 --fp16-scale-window 128 " \
                   f"--max-epoch 10 --save-dir {bin_dir} --find-unused-parameters " \
                   f"--best-checkpoint-metric accuracy --maximize-best-checkpoint-metric "

        print(tune_cmd)
        os.system(tune_cmd)

        # inference
        roberta = RobertaModel.from_pretrained(
            model_name_or_path=bin_dir,
            checkpoint_file='checkpoint_best.pt',
            data_name_or_path=bin_dir)

        label_fn = lambda label: roberta.task.label_dictionary.string(
            [label + roberta.task.label_dictionary.nspecial])
        ncorrect, nsamples = 0, 0
        roberta.cuda()
        roberta.eval()

        test_ds = fine_tune_params["test_ds"]

        if HEAD_NAME == "rte_head":
            with open(test_ds) as fin:
                fin.readline()
                for index, line in enumerate(fin):
                    tokens = line.strip().split('\t')
                    sent1, sent2, target = tokens[1], tokens[2], tokens[3]
                    # print(sent1, sent2, target)
                    tokens = roberta.encode(sent1, sent2)
                    prediction = roberta.predict(HEAD_NAME,
                                                 tokens).argmax().item()
                    prediction_label = label_fn(prediction)
                    ncorrect += int(prediction_label == target)
                    nsamples += 1
            print('| Accuracy: ', float(ncorrect) / float(nsamples))

        elif HEAD_NAME == "mrpc_head":
            with open(test_ds) as fin:
                fin.readline()
                for index, line in enumerate(fin):
                    tokens = line.strip().split('\t')
                    target, sent1, sent2 = tokens[0], tokens[3], tokens[4]
                    tokens = roberta.encode(sent1, sent2)
                    prediction = roberta.predict(HEAD_NAME,
                                                 tokens).argmax().item()
                    prediction_label = label_fn(prediction)
                    # print(sent1, sent2, target, prediction_label)
                    ncorrect += int(prediction_label == target)
                    nsamples += 1
            print('| Accuracy: ', float(ncorrect) / float(nsamples))

        elif HEAD_NAME == "mnli_head":

            with open(test_ds) as fin:
                fin.readline()
                for index, line in enumerate(fin):
                    tokens = line.strip().split('\t')
                    sent1, sent2, target = tokens[8], tokens[9], tokens[15]
                    # print(sent1, sent2, target)
                    tokens = roberta.encode(sent1, sent2)
                    prediction = roberta.predict(HEAD_NAME,
                                                 tokens).argmax().item()
                    prediction_label = label_fn(prediction)
                    ncorrect += int(prediction_label == target)
                    nsamples += 1
                    Matched_Accuracy = float(ncorrect) / float(nsamples)

            print('| Matched Accuracy: ', Matched_Accuracy)
        else:
            with open(test_ds) as fin:
                fin.readline()
                for index, line in enumerate(fin):
                    tokens = line.strip().split('\t')
                    sent1, sent2, target = tokens[1], tokens[2], tokens[3]
                    tokens = roberta.encode(sent1, sent2)
                    prediction = roberta.predict(HEAD_NAME,
                                                 tokens).argmax().item()
                    prediction_label = label_fn(prediction)
                    # print(sent1, sent2, target, prediction_label)
                    ncorrect += int(prediction_label == target)
                    nsamples += 1
            print('| Accuracy: ', float(ncorrect) / float(nsamples))
