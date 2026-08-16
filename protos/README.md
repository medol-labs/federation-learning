The remaining protobuf file is the runtime data-plane receiver used for
parameter transfer between training and aggregation nodes.

Command to compile the proto:

```
python -m grpc_tools.protoc --python_out=../python/gemifl/pbs --grpc_python_out=../python/gemifl/pbs -I. *.proto
```
