# grpc_client.py

import cloudpickle
from collections import defaultdict
import time
import logging
from concurrent import futures
import grpc
import traceback
import os

from gemifl.pbs import data_client_pb2
from gemifl.pbs import data_client_pb2_grpc


logging.basicConfig(format='%(asctime)s %(levelname)-8s %(message)s', level=logging.INFO, datefmt='%Y-%m-%d %H:%M:%S')

logger = logging.getLogger(__name__)

MAX_CHUNK_SIZE = 1000 * 1024 * 1024  # Let's assume 500MB chunks for now.
GRPC_OPTIONS=[
        ('grpc.max_send_message_length', MAX_CHUNK_SIZE*3//2),
        ('grpc.max_receive_message_length', MAX_CHUNK_SIZE*3//2),
] # This is the grpc optinons, I make it a liitle larger than the max chunk size

# Constants
CHUNK_KEY_SUFFIX = "_chunk_"
CHUNK_NUMKEY_SUFFIX = 'CHUNK_NUM_'

stored_data = defaultdict(dict)
stored_data_loads_indicator_num = defaultdict(dict)
class Receiver(data_client_pb2_grpc.ReceiverServicer):

    def receive(self, request, context):
        ip = context.peer()
        node_name = request.node_name
        key = request.key
        logger.info(f"Received from {node_name} with key {key}")

        # Check if the key indicates a chunk
        if CHUNK_NUMKEY_SUFFIX in key:
            main_key, chunk_total_num = key.split(CHUNK_NUMKEY_SUFFIX)
            stored_data_loads_indicator_num[node_name][main_key] = int(chunk_total_num)
            return data_client_pb2.Reply(succeed=True)

        if CHUNK_KEY_SUFFIX in key:
            main_key, chunk_num = key.split(CHUNK_KEY_SUFFIX)
            if main_key not in stored_data[node_name]:
                stored_data[node_name][main_key] = []
            stored_data[node_name][main_key].append((int(chunk_num), request.data))
            return data_client_pb2.Reply(succeed=True)
        
        stored_data_loads_indicator_num[node_name][key] = 0
        stored_data[node_name][key] = request.data
        return data_client_pb2.Reply(succeed=True)


class Client:

    def __init__(self, node_config, my_name):
        self.node_config = node_config
        self.my_name = my_name
        self.server = grpc.server(futures.ThreadPoolExecutor(max_workers=10),options=GRPC_OPTIONS)
        data_client_pb2_grpc.add_ReceiverServicer_to_server(
            Receiver(), self.server)
        self.server.add_insecure_port(self._bind_address(node_config[my_name]))
        self.server.start()
        logger.info('GRPC Server started...')
        self.channel_status = dict()

    def _bind_address(self, configured_address):
        override = os.getenv("RUNTIME_ENGINE_DATA_BIND_ADDRESS") or os.getenv("GEMIFL_DATA_BIND_ADDRESS")
        if override:
            return override
        if ":" not in configured_address:
            return configured_address
        return f"0.0.0.0:{configured_address.rsplit(':', 1)[1]}"

    def send(self, target_role, key, val, time_out=10):
        logger.info(f"Send to {target_role} with key {key}")
        try:
          serialized_data = cloudpickle.dumps(val)

          # If the data is too large, split it and send chunks
          if len(serialized_data) > MAX_CHUNK_SIZE:
              chunks = [serialized_data[i:i+MAX_CHUNK_SIZE] for i in range(0, len(serialized_data), MAX_CHUNK_SIZE)]
              chunk_key_total_num = f"{key}{CHUNK_NUMKEY_SUFFIX}{len(chunks)}"
              self._send_single(target_role, chunk_key_total_num, b'0', time_out)
              for idx, chunk in enumerate(chunks):
                  chunk_key = f"{key}{CHUNK_KEY_SUFFIX}{idx}"
                  self._send_single(target_role, chunk_key, chunk, time_out)
              return True
          
          # If the data isn't too large, send it as it is
          return self._send_single(target_role, key, serialized_data, time_out)
        except Exception:
            logger.error(traceback.format_exc()) 
            return False

    def _send_single(self, target_role, key, serialized_data, time_out):
        for i in range(time_out):
            try:
                if self.node_config[target_role] in self.channel_status:
                    channel = self.channel_status[self.node_config[target_role]]
                else:
                    channel = grpc.insecure_channel(
                        self.node_config[target_role],options=GRPC_OPTIONS)
                    self.channel_status[self.node_config[target_role]] = channel
                stub = data_client_pb2_grpc.ReceiverStub(channel)
                stub.receive(
                    data_client_pb2.Data(node_name=self.my_name,
                                         key=key,
                                         data=serialized_data))
                return True
            except Exception as e:
                logger.info(f"send to {target_role} with key {key} failed {time_out}, retrying...")
                logger.error(traceback.format_exc())
                time.sleep(1)
        logging.error(f"send to {target_role} with key {key} timed out.")
        return False

    def get(self, target_role, key, time_out=100, time_sleep=1):
        '''
        get msg with re-try and sleep wait

        time_out : how many times to re-try until successfully get from target
        time_sleep: when get failed, sleep `time_sleep` secend to re-get
        '''
        try:        
            for i in range(time_out):
                logger.info(f"Try to get {target_role} {key}, times: {i}")
                if target_role in stored_data and key in stored_data[target_role]:
                    res = stored_data[target_role][key]
                    if stored_data_loads_indicator_num[target_role][key] == -1:
                        return res
                    else:
                        
                        if isinstance(res,list):
                            for _ in range(time_out):
                                logger.info(f"Try to get all split data")
                                if len(res)!=stored_data_loads_indicator_num[target_role][key]:
                                    time.sleep(1)
                                else:
                                    break
                            else:
                                raise Exception(f"receive from {target_role} with key {key} time out.")
                            res = sorted(res, key= lambda x: x[0])
                            tmp_bytes = res[0][1]
                            for r in res[1:]:
                                tmp_bytes += r[1]
                            res = cloudpickle.loads(tmp_bytes)

                        else:
                            res = cloudpickle.loads(res)
                        stored_data_loads_indicator_num[target_role][key] = -1
                        # stored_data[target_role][key] = res
                        del stored_data[target_role][key] #read and delete to save memory
                    return res
                else:
                    time.sleep(time_sleep)
            else:
                raise Exception(f"receive from {target_role} with key {key} time out.")
        except:
            raise Exception(f"receive from {target_role} with key {key} time out.")

    def get_all(self, key, time_out=100):
        res_list = []
        roles = list(self.node_config.keys())
        freq = 0.2
        cur_time = 0
        while roles:
            role = roles.pop(0)
            if role == self.my_name:
                continue
            elif key in stored_data[role]:
                res_list.append(self.get(role,key))
            else:
                roles.append(role)
                cur_time += freq
                time.sleep(freq)
                if cur_time > time_out:
                    raise Exception(f"Get all timeout")
        return res_list

    def send_all(self, key, val, time_out=10):
        roles = list(self.node_config.keys())
        while roles:
            role = roles.pop(0)
            if role == self.my_name:
                continue
            else:
                self.send(role, key, val, time_out)
