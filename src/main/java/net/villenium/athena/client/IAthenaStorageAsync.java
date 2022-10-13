package net.villenium.athena.client;

import net.villenium.athena.client.util.Constant;

public interface IAthenaStorageAsync {

    /**
     * Асинхронное обновление или установка объекта в хранилище.
     *
     * @param id      айди объекта.
     * @param data    объект.
     * @param options параметры.
     */
    void upsert(String id, Object data, DataOptions options);

    /**
     * Асинхронное обновление или установка с базовыми опциями.
     *
     * @param id   айди объекта.
     * @param data объект.
     */
    default void upsert(String id, Object data) {
        upsert(id, data, Constant.DEFAULT_OPTIONS);
    }

    /**
     * Асинхронное получение объекта из хранилища.
     *
     * @param id      айди объекта.
     * @param type    тип объекта.
     * @param options параметры.
     * @return объект из хранилища.
     */
    <T> T getObject(String id, Class<T> type, DataOptions options);

    /**
     * Асинхронное получение объекта из хранилища с базовыми опциями.
     *
     * @param id   айди объекта.
     * @param type тип объекта.
     * @return объект из хранилища.
     */
    default <T> T getObject(String id, Class<T> type) {
        return getObject(id, type, Constant.DEFAULT_OPTIONS);
    }

}
