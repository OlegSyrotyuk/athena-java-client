package net.villenium.athena.client.impl.local;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import net.villenium.athena.client.Storage;
import net.villenium.athena.client.StorageManager;

public class LocalStorageManager implements StorageManager {

    private MongoClient client;
    private MongoDatabase database;

    @Override
    public void start(String target, String client, String key) {
        throw new UnsupportedOperationException("You can't use this method for local storage.");
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("You can't use this method for local storage.");
    }

    public void connect(String uri, String databaseName) {
        client = new MongoClient(new MongoClientURI(uri, MongoClientOptions.builder().retryWrites(true).connectionsPerHost(1000)));
        database = client.getDatabase(databaseName);
        System.out.println("MongoDB connection successfully!");
    }

    public void disconnect() {
        client.close();
        System.out.println("MongoDB disconnection successfully!");
    }

    @Override
    public <T> Storage<T> create(String name, Gson gson, Class<T> type) {
        if (database.getCollection(name).countDocuments() < 0) {
            database.createCollection(name);
        }
        return new LocalStorage<>(database.getCollection(name), gson, type);
    }
}
