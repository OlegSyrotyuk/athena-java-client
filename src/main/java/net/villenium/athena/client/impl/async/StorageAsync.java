package net.villenium.athena.client.impl.async;

import com.google.gson.Gson;
import com.google.protobuf.Empty;
import io.grpc.CallCredentials;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.villenium.athena.AthenaGrpc;
import net.villenium.athena.AthenaService;
import net.villenium.athena.client.DataOptions;
import net.villenium.athena.client.IAthenaStorageAsync;
import net.villenium.athena.client.util.Helpers;

@RequiredArgsConstructor
public class StorageAsync<T> implements IAthenaStorageAsync<T> {

    private final AthenaGrpc.AthenaStub stub;
    private final CallCredentials credentials;
    private final String storageName;
    private final Gson gson;
    private final Class<T> type;

    @Override
    public void upsert(String id, Object data, DataOptions options) {
        AthenaService.SetObjectRequest request = AthenaService.SetObjectRequest.newBuilder()
                .setStorage(storageName)
                .setId(id)
                .setData(gson.toJson(data))
                .setOptions(gson.toJson(options))
                .build();
        Helpers.<Empty>unaryAsyncCall(streamObserver -> {
            stub.withCallCredentials(credentials).upsert(request, streamObserver);
        });
    }

}
