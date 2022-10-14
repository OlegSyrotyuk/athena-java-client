package net.villenium.athena.client.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.villenium.athena.client.DataOptions;
import net.villenium.athena.client.impl.AthenaStorageStrategy;

import java.util.concurrent.TimeUnit;

public class Constant {

    public static final DataOptions DEFAULT_OPTIONS = DataOptions.builder()
            .cache(true)
            .cacheTime(TimeUnit.HOURS.toSeconds(4))
            .build();

    public static final DataOptions WITHOUT_CACHING = DataOptions.builder()
            .cache(false)
            .build();

    public static final Gson ATHENA_DEFAULT_GSON = new GsonBuilder()
            .setFieldNamingStrategy(new AthenaStorageStrategy())
            .create();

}
