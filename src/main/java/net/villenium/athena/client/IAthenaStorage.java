package net.villenium.athena.client;

import net.villenium.athena.client.impl.find.FindRequest;
import net.villenium.athena.client.util.Constant;

import java.util.List;

public interface IAthenaStorage {

    /**
     * Начать общение с сервисом.
     *
     * @param target host:port
     * @param client имя клиента.
     * @param key    ключ авторизации.
     */
    void start(String target, String client, String key);

    /**
     * Остановить общение с сервисом.
     */
    void stop();

    /**
     * Получение объекта из хранилища.
     *
     * @param id      айди объекта.
     * @param type    тип объекта.
     * @param options параметры.
     * @return объект из хранилища.
     */
    <T> T getObject(String id, Class<T> type, DataOptions options);

    /**
     * Получение объекта из хранилища с базовыми опциями.
     *
     * @param id   айди объекта.
     * @param type тип объекта.
     * @return объект из хранилища.
     */
    default <T> T getObject(String id, Class<T> type) {
        return getObject(id, type, Constant.DEFAULT_OPTIONS);
    }

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
        upsert(id, data, Constant.DEFAULT_OPTIONS);
    }

    /**
     * Получить топ по определенному полю.
     *
     * @param stat      поле объекта.
     * @param count     количество объектов.
     * @param ascending true - от большего к меньшему, false - от меньшего к большему.
     * @return список из объектов.
     */
    <T> List<T> getTopByStat(String stat, int count, boolean ascending, Class<T> type);

    /**
     * Получить все объекты поле которых равняется необходимому значению (числа).
     * @param request параметры запроса.
     * @return все объекты коллекции поле которого равняется значению.
     */
    <T> List<T> findAll(FindRequest request, Class<T> type);

    /**
     * Вызвать билдер запросов.
     * @param type тип объекта.
     * @return билдер.
     */
    <T> IFindRequestBuilder findAll(Class<T> type);

    /**
     * Асинхронные методы для работы с хранилищем.
     *
     * @return реализация асинхронного хранилища.
     */
    IAthenaStorageAsync async();

}
