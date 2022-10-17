package net.villenium.athena.client.impl;

import com.google.gson.Gson;
import net.villenium.athena.client.IAthenaStorage;
import net.villenium.athena.client.IAthenaStorageBuilder;

public class StorageBuilder<T> implements IAthenaStorageBuilder<T> {

    private String storageName;
    private Gson gson;
    private Class<T> type;

    @Override
    public IAthenaStorageBuilder<T> create(String name) {
        this.storageName = name;
        return this;
    }

    @Override
    public IAthenaStorageBuilder<T> setGson(Gson gson) {
        this.gson = gson;
        return this;
    }

    @Override
    public IAthenaStorageBuilder<T> setType(Class<T> type) {
        this.type = type;
        return this;
    }

    @Override
    public IAthenaStorage<T> build() {
        return new Storage<>(storageName, gson, type);
    }
}
