package net.villenium.athena.client.impl;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import lombok.RequiredArgsConstructor;
import net.villenium.athena.AthenaGrpc;
import net.villenium.athena.AthenaService;
import net.villenium.athena.client.*;
import net.villenium.athena.client.impl.async.StorageAsync;
import net.villenium.athena.client.impl.find.FindRequest;
import net.villenium.athena.client.impl.find.FindRequestBuilder;
import net.villenium.athena.client.impl.pool.AthenaObjectPool;
import net.villenium.athena.client.impl.pool.ReadOnlyObjectPool;

import java.util.List;

@RequiredArgsConstructor
public class Storage<T> implements IAthenaStorage<T> {

    private final String storageName;
    private final Gson gson;

    private final Class<T> type;
    private ManagedChannel channel;
    private final AthenaGrpc.AthenaBlockingStub stub;

    private final CallCredentials credentials;

    @Override
    public Class<T> getType() {
        return type;
    }

//    @Override
//    public void start(String target, String client, String key) {
//        credentials = new JwtCredential(client, key);
//        channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
//        stub = AthenaGrpc.newBlockingStub(channel);
//        AthenaService.CreateRequest request = AthenaService.CreateRequest.newBuilder()
//                .setName(storageName)
//                .build();
//        stub.withCallCredentials(credentials).createStorage(request);
//    }
//
//    @Override
//    public void stop() {
//        channel.shutdownNow();
//    }

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
    public List<T> getTopByStat(String stat, int count, boolean ascending) {
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
    public List<T> findAll(FindRequest findRequest) {
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
    public IFindRequestBuilder<T> find() {
        return new FindRequestBuilder<>(this);
    }

    @Override
    public IAthenaStorageAsync<T> async() {
        return new StorageAsync<>(AthenaGrpc.newStub(channel), credentials, storageName, gson, type);
    }

    @Override
    public ObjectPool<T> newObjectPool() {
        return new AthenaObjectPool<>(this);
    }

    @Override
    public ReadOnlyObjectPool<T> newReadOnlyObjectPool() {
        return new ReadOnlyObjectPool<>(this);
    }
}
