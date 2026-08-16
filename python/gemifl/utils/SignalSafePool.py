import sys
import signal
import logging
from multiprocessing import Pool, active_children

class SignalSafePool:
    """
    A custom pool class that gracefully handles termination signals.
    It also directly executes functions if the number of processes is set to 1.
    """

    def __init__(self, processes=None, *args, **kwargs):
        self.direct_execution = processes == 1
        if not self.direct_execution:
            self._pool = Pool(processes, *args, **kwargs)
            self._set_signal_handlers()
            logging.info(f"Initialized SignalSafePool with {processes} processes.")
        else:
            self._pool = None
            logging.info("Initialized SignalSafePool in single-threaded mode.")

    def terminate_child_processes(self, signalNumber, frame):
        """
        Terminate all child processes gracefully.
        """
        if self._pool:
            active = active_children()
            for child in active:
                child.terminate()
            sys.exit()

    def apply_async(self, func, *args, **kwargs):
        if self.direct_execution:
            return DirectExecutionResult(func, args, kwargs)
        else:
            return self._pool.apply_async(func, *args, **kwargs)

    def close(self):
        if self._pool:
            self._pool.close()

    def join(self):
        if self._pool:
            self._pool.join()

    def _set_signal_handlers(self):
        """
        Set signal handlers for termination signals.
        """
        signal.signal(signal.SIGTERM, self.terminate_child_processes)


class DirectExecutionResult:
    """
    A mock of the Pool result for direct execution.
    """
    
    def __init__(self, func, args, kwargs):
        self.func = func
        self.args = args
        self.kwargs = kwargs

    def get(self, *args, **kwargs):
        return self.func(*self.args, **self.kwargs)




# Usage:
'''
with SignalSafePool(processes=4) as pool:
    result = pool.apply_async(some_function, args=(some_args,))
'''