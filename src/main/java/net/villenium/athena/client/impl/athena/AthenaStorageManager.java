package net.villenium.athena.client.impl.athena;

import com.google.gson.Gson;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.villenium.athena.AthenaGrpc;
import net.villenium.athena.AthenaService;
import net.villenium.athena.client.Storage;
import net.villenium.athena.client.StorageManager;
import net.villenium.athena.client.impl.athena.auth.JwtCredential;
import net.villenium.athena.client.util.Athena;

public class AthenaStorageManager implements StorageManager {

    private ManagedChannel channel;
    private AthenaGrpc.AthenaBlockingStub stub;

    private CallCredentials credentials;

    @Override
    public void start(String target, String client, String key) {
        Athena.LOGGER.info("Connection to athena...");
        credentials = new JwtCredential(client, key);
        channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        stub = AthenaGrpc.newBlockingStub(channel);
        Athena.LOGGER.info("Successfully connection to athena.");
    }

    @Override
    public void stop() {
        Athena.LOGGER.info("Disconnecting from athena...");
        channel.shutdownNow();
        Athena.LOGGER.info("Successfully disconnect from athena.");
    }

    @Override
    public <T> Storage<T> create(String name, Gson gson, Class<T> type) {
        AthenaService.CreateRequest request = AthenaService.CreateRequest.newBuilder()
                .setName(name)
                .build();
        stub.withCallCredentials(credentials).createStorage(request);
        return new AthenaStorage<T>(name, gson, type, stub, credentials);
    }
}
