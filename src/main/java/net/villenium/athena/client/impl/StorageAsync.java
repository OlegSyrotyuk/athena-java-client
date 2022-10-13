package net.villenium.athena.client.impl;

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
public class StorageAsync implements IAthenaStorageAsync {

    private final AthenaGrpc.AthenaStub stub;
    private final CallCredentials credentials;
    private final String storageName;
    private final Gson gson;

    @Override
    public void upsert(String id, Object data, DataOptions options) {
        AthenaService.SetObjectRequest request = AthenaService.SetObjectRequest.newBuilder()
                .setStorage(storageName)
                .setId(id)
                .setData(gson.toJson(data))
                .setOptions(gson.toJson(options))
                .build();
        stub.withCallCredentials(credentials).upsert(request, new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty value) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }

    @SneakyThrows
    @Override
    public <T> T getObject(String id, Class<T> type, DataOptions options) {
        AthenaService.GetObjectRequest request = AthenaService.GetObjectRequest.newBuilder()
                .setStorage(storageName)
                .setId(id)
                .setOptions(gson.toJson(options))
                .build();
        return gson.fromJson(Helpers.<AthenaService.GetObjectResponse>unaryAsyncCall(
                observer -> stub.withCallCredentials(credentials).getObject(request, observer)).get().getData(), type);
    }

}
