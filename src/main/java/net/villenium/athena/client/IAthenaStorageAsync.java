package net.villenium.athena.client;

import net.villenium.athena.client.util.Constant;

public interface IAthenaStorageAsync<T> {

    /**
     * Асинхронное обновление или установка объекта в хранилище.
     *
     * @param id      айди объекта.
     * @param data    объект.
     * @param options параметры.
     */
    void upsert(String id, T data, DataOptions options);

    /**
     * Асинхронное обновление или установка с базовыми опциями.
     *
     * @param id   айди объекта.
     * @param data объект.
     */
    default void upsert(String id, T data) {
        upsert(id, data, Constant.DEFAULT_OPTIONS);
    }

}
