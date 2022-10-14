package net.villenium.athena.client.impl;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.villenium.athena.AthenaGrpc;
import net.villenium.athena.AthenaService;
import net.villenium.athena.client.DataOptions;
import net.villenium.athena.client.IAthenaStorage;
import net.villenium.athena.client.IAthenaStorageAsync;
import net.villenium.athena.client.IFindRequestBuilder;
import net.villenium.athena.client.impl.auth.JwtCredential;
import net.villenium.athena.client.impl.async.StorageAsync;
import net.villenium.athena.client.impl.find.FindRequest;
import net.villenium.athena.client.impl.find.FindRequestBuilder;

import java.util.List;

@RequiredArgsConstructor
public class Storage implements IAthenaStorage {

    private final String storageName;
    private final Gson gson;
    private ManagedChannel channel;
    private AthenaGrpc.AthenaBlockingStub stub;

    private CallCredentials credentials;

    @Override
    public void start(String target, String client, String key) {
        credentials = new JwtCredential(client, key);
        channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        stub = AthenaGrpc.newBlockingStub(channel);
        AthenaService.CreateRequest request = AthenaService.CreateRequest.newBuilder()
                .setName(storageName)
                .build();
        stub.withCallCredentials(credentials).createStorage(request);
    }

    @Override
    public void stop() {
        channel.shutdownNow();
    }

    @SneakyThrows
    @Override
    public <T> T getObject(String id, Class<T> type, DataOptions options) {
        AthenaService.GetObjectRequest request = AthenaService.GetObjectRequest.newBuilder()
                .setStorage(storageName)
                .setId(id)
                .setOptions(gson.toJson(options))
                .build();
        AthenaService.GetObjectResponse response = stub.withCallCredentials(credentials).getObject(request);
        return gson.fromJson(response.getData(), type);
    }

    @Override
    public void upsert(String id, Object data, DataOptions options) {
        AthenaService.SetObjectRequest request = AthenaService.SetObjectRequest.newBuilder()
                .setStorage(storageName)
                .setId(id)
                .setData(gson.toJson(data))
                .setOptions(gson.toJson(options))
                .build();
        stub.withCallCredentials(credentials).upsert(request);
    }

    @Override
    public <T> List<T> getTopByStat(String stat, int count, boolean ascending, Class<T> type) {
        List<T> list = Lists.newArrayList();
        AthenaService.GetTopByStatRequest request = AthenaService.GetTopByStatRequest
                .newBuilder()
                .setStorage(storageName)
                .setStat(stat)
                .setCount(count)
                .setAscending(ascending)
                .build();
        stub.withCallCredentials(credentials).getTopByStat(request).forEachRemaining(response -> {
            list.add(gson.fromJson(response.getData(), type));
        });
        return list;
    }

    @Override
    public <T> List<T> findAll(FindRequest findRequest, Class<T> type) {
        List<T> list = Lists.newArrayList();
        AthenaService.FindAllRequest request = AthenaService.FindAllRequest
                .newBuilder()
                .setStorage(storageName)
                .setRequest(gson.toJson(findRequest))
                .build();
        stub.withCallCredentials(credentials).findAll(request).forEachRemaining(response -> {
            list.add(gson.fromJson(response.getData(), type));
        });
        return list;
    }

    @Override
    public <T> IFindRequestBuilder findAll(Class<T> type) {
        return new FindRequestBuilder<>(this, type);
    }

    @Override
    public IAthenaStorageAsync async() {
        return new StorageAsync(AthenaGrpc.newStub(channel), credentials, storageName, gson);
    }
}
