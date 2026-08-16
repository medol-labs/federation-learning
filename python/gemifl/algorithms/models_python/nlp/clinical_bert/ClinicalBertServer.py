from gemifl.utils.base_modules import Federation, ConfigParser

import sys
import logging

logging.basicConfig(level=logging.INFO, stream=sys.stdout)


class ClinicalBertServer:
    def __init__(self, config) -> None:
        self.params_parser = ConfigParser(config)
        self.fed_client = Federation(
            self.params_parser.my_name, self.params_parser.roles, self.params_parser.node_config)

    def run(self):
        epochs = self.params_parser.model_parameter.get("epochs")

        for epoch in range(epochs):
            # model averaging
            weight_key = f"net_params_{epoch}"
            averaging_net_params = self.fed_client.average_weights(
                key=weight_key, time_sleep=20)

            logging.info(
                f"current epoch {epoch} finished.")
