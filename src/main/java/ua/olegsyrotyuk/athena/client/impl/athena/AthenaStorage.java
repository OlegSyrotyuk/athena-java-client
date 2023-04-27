package ua.olegsyrotyuk.athena.client.impl.athena;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import lombok.RequiredArgsConstructor;
import net.villenium.athena.AthenaGrpc;
import net.villenium.athena.AthenaService;
import ua.olegsyrotyuk.athena.client.impl.find.AthenaFindRequestBuilder;
import ua.olegsyrotyuk.athena.client.impl.pool.AthenaObjectPool;
import ua.olegsyrotyuk.athena.client.impl.pool.AthenaReadOnlyObjectPool;
import ua.olegsyrotyuk.athena.client.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class AthenaStorage<T> implements Storage<T> {

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
    public RequestBuilder<T> find() {
        return new AthenaFindRequestBuilder<>(this);
    }

    @Override
    public T findById(String id) {
        AthenaService.GetObjectRequest request = AthenaService.GetObjectRequest
                .newBuilder()
                .setStorage(storageName)
                .setId(id)
                .build();
        AthenaService.GetObjectResponse response = stub.withCallCredentials(credentials).findById(request);
        return gson.fromJson(response.getData(), getType());
    }


    @Override
    public ObjectPool<T> newObjectPool(Map<String, T> source) {
        return new AthenaObjectPool<T>(this, source);
    }

    @Override
    public ReadOnlyObjectPool<T> newReadOnlyObjectPool(Map<String, T> source) {
        return new AthenaReadOnlyObjectPool<T>(this, source);
    }
}
