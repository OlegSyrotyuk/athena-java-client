package net.villenium.athena.client.impl;

import com.google.gson.Gson;
import net.villenium.athena.client.IAthenaStorage;
import net.villenium.athena.client.IAthenaStorageBuilder;

public class StorageBuilder implements IAthenaStorageBuilder {

    private String storageName;
    private Gson gson;

    @Override
    public IAthenaStorageBuilder create(String name) {
        this.storageName = name;
        return this;
    }

    @Override
    public IAthenaStorageBuilder setGson(Gson gson) {
        this.gson = gson;
        return this;
    }

    @Override
    public IAthenaStorage build() {
        return new Storage(storageName, gson);
    }
}
