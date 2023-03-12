package net.villenium.athena.client;

import net.villenium.athena.client.impl.find.FindRequest;
import net.villenium.athena.client.impl.pool.ReadOnlyObjectPool;
import net.villenium.athena.client.util.Athena;

import java.util.List;

public interface IAthenaStorage<T> {

    /**
     * Получить тип хранилища.
     *
     * @return тип хранилища.
     */
    Class<T> getType();

    /**
     * Обновление или установка объекта в хранилище.
     *
     * @param id      айди объекта.
     * @param data    объект.
     * @param options параметры.
     */
    void upsert(String id, Object data, DataOptions options);

    /**
     * Обновление или установка объекта в хранилище с базовыми опциями.
     *
     * @param id   айди объекта.
     * @param data объект.
     */
    default void upsert(String id, Object data) {
        upsert(id, data, Athena.DEFAULT_OPTIONS);
    }

    /**
     * Получить топ по определенному полю.
     *
     * @param stat      поле объекта.
     * @param count     количество объектов.
     * @param ascending true - от большего к меньшему, false - от меньшего к большему.
     * @return список из объектов.
     */
    List<T> getTopByStat(String stat, int count, boolean ascending);

    /**
     * Получить все объекты поле которых равняется необходимому значению (числа).
     *
     * @param request параметры запроса.
     * @return все объекты коллекции поле которого равняется значению.
     */
    List<T> findAll(FindRequest request);

    /**
     * Вызвать билдер запросов.
     *
     * @return билдер.
     */
    IFindRequestBuilder<T> find();

    /**
     * Получить объект по айди (Ищет объект независимо от регистра айди).
     *
     * @param id айди объекта.
     * @return объект или null.
     */
    T findById(String id);

    /**
     * Асинхронные методы для работы с хранилищем.
     *
     * @return реализация асинхронного хранилища.
     */
    @Deprecated
    IAthenaStorageAsync<T> async();

    /**
     * Создать новый пул объектов.
     *
     * @return пул объектов.
     */
    ObjectPool<T> newObjectPool();

    /**
     * Создать новый пул объектов с которого можно только читать объекты.
     * @return пул объектов.
     */
    IReadOnlyObjectPool<T> newReadOnlyObjectPool();

}
