package net.villenium.athena.client;

import com.google.gson.Gson;

public interface IAthenaStorageBuilder<T> {

    /**
     * Создать билдер хранилища.
     *
     * @param name имя.
     * @return этот билдер
     */
    IAthenaStorageBuilder<T> create(String name);

    /**
     * Установить Gson.
     *
     * @param gson Gson.
     * @return этот билдер
     */
    IAthenaStorageBuilder<T> setGson(Gson gson);

    /**
     * Установить тип хранилища.
     *
     * @param type тип.
     * @return этот билдер.
     */
    IAthenaStorageBuilder<T> setType(Class<T> type);

    /**
     * Создать объект хранилища.
     *
     * @return хранилище.
     */
    IAthenaStorage<T> build();

}
