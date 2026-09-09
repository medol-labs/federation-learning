import logging
import os

LOG_PATH = "./tmp/runtime-engine"
Task_id = ""
class TaskFilter(logging.Filter):
    def filter(self, record):
        record.task_id = Task_id
        return True

#I prefer use logging instead of logger at here as I want to 
#save all the log in to one file. Logger is not thread safe.
class Logger(logging.Logger):
    
    def __init__(self, task_id = "test", logger_level = "INFO", name = "user", logpath=LOG_PATH):
        
        # ‘__name__’ is a built-in variable in Python that represents the name of the current module
        # by passing '__name__' as an argument, we ensure that each module uses a separate Logger object
        super().__init__(name)
        self.setLevel(logger_level)
        self.addFilter(TaskFilter())

        # create path
        temp_log_path = os.path.join(logpath, task_id, name)
        if not os.path.exists(temp_log_path):
            os.makedirs(temp_log_path)

        global Task_id
        Task_id = task_id
        log_format = '%(asctime)s [%(levelname)s] [%(process)s:%(thread)s] - [%(module)s.%(funcName)s] [Line %(lineno)d]: %(task_id)s %(message)s'
        fmt = logging.Formatter(log_format)
        # file handler
        log_file_name = os.path.join(temp_log_path, "log.txt")
        file_handler = logging.FileHandler(log_file_name)
        file_handler.setLevel(logger_level)
        file_handler.setFormatter(fmt)
        self.addHandler(file_handler)
        # steam handler
        stream_handler = logging.StreamHandler()
        stream_handler.setLevel(logger_level)
        stream_handler.setFormatter(fmt)
        self.addHandler(stream_handler)
        
