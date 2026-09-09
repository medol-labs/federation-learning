# save metrics to local path
import os
import json


class MetricRecord:
    # save 'auc', 'acc', 'recall' and so on
    def __init__(self,
                 root_dir="./tmp/runtime-engine",
                 taskId=100,
                 node_name="") -> None:
        self.root_dir = root_dir
        self.taskId = taskId
        self.node_name = node_name
        self.n_iter = 0

        self.save_path = os.path.join(self.root_dir, self.taskId,
                                      self.node_name, "metric")

        if not os.path.exists(self.save_path):
            os.makedirs(self.save_path)

    def save(self, current_metrics={}):
        if "n_iter" in current_metrics:
            n = current_metrics["n_iter"]
        else:
            n = self.n_iter

        # add to total metirc dict
        for key, val in current_metrics.items():
            key = key.lower()
            if key == "status":
                sav_path = os.path.join(self.root_dir, self.taskId,
                                        self.node_name, "status.txt")
                with open(sav_path, "w") as f:
                    json_str = json.dumps(val, indent=4)
                    f.write(json_str)

            else:
                if key != "n_iter":
                    save_file = os.path.join(self.save_path, f"{key}.txt")

                    if self.n_iter == 0:
                        with open(save_file, "w") as f:
                            f.writelines("iter\tvalue\n")
                            f.writelines(f"{n}\t{val}\n")
                    else:
                        with open(save_file, "a") as f:
                            f.writelines(f"{n}\t{val}\n")

        self.n_iter += 1
