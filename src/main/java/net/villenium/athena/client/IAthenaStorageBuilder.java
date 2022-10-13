package net.villenium.athena.client;

import com.google.gson.Gson;

public interface IAthenaStorageBuilder {

    /**
     * Создать билдер хранилища.
     *
     * @param name имя.
     * @return этот билдер
     */
    IAthenaStorageBuilder create(String name);

    /**
     * Установить Gson.
     *
     * @param gson Gson.
     * @return этот билдер
     */
    IAthenaStorageBuilder setGson(Gson gson);

    /**
     * Создать объект хранилища.
     *
     * @return хранилище.
     */
    IAthenaStorage build();

}
