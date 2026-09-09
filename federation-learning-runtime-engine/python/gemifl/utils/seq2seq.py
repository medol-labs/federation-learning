import torch


def seq2seq_avg(model, client, my_node_name, current_epoch, selected_node,
                sorted_nodes):
    # averaging all model weights
    local_model_weights = model.state_dict()

    # send local model weights to selected node
    if my_node_name != selected_node:
        # send model weight to center nodes for aggregation
        client.send(target_role=selected_node,
                    key=my_node_name + "_net_params_" + str(current_epoch),
                    val=local_model_weights)
        averaging_net_params = client.get(target_role=selected_node,
                                          key=selected_node + "_net_params_" +
                                          str(current_epoch))

    else:
        # averaging model weights from receiving parameters
        averaging_net_params = local_model_weights
        num = 1
        for tmp_node in sorted_nodes:
            if tmp_node != my_node_name:
                tmp_val = client.get(target_role=tmp_node,
                                     key=tmp_node + "_net_params_" +
                                     str(current_epoch))

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
                            key=my_node_name + "_net_params_" +
                            str(current_epoch),
                            val=averaging_net_params)

    return averaging_net_params