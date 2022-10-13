package net.villenium.athena.client.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.grpc.Metadata;
import net.villenium.athena.client.DataOptions;
import net.villenium.athena.client.impl.AthenaStorageStrategy;

import java.util.concurrent.TimeUnit;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class Constant {

    public static final DataOptions DEFAULT_OPTIONS = DataOptions.builder()
            .cache(true)
            .cacheTime(TimeUnit.HOURS.toSeconds(4))
            .build();

    public static final Gson ATHENA_DEFAULT_GSON = new GsonBuilder()
            .setFieldNamingStrategy(new AthenaStorageStrategy())
            .create();

    public static final String JWT_SIGNING_KEY = "L8hHXsaQOUjk5rg7XPGv4eL36anlCrkMz8CJ0i/8E/0=";
    public static final String BEARER_TYPE = "Bearer";

    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);

}
