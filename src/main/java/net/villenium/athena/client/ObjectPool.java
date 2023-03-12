package net.villenium.athena.client;

import java.util.concurrent.TimeUnit;

public interface ObjectPool<T> {

    /**
     * Установить базовый объект пула.
     *
     * @param object экземпляр объекта с базовыми значениями.
     */
    void setDefaultObject(T object);

    /**
     * Включить ли авто сохранение объектов в хранилище.
     * @param value значение.
     */
    void setAutoSave(boolean value);

    /**
     * Время между авто сохранениями.
     * @param value значение в минутах.
     */
    void setAutoSaveTime(long value);

    void setDebug(boolean value);

    /**
     * Получить объект из кэша.
     * Если объект отсутствует, метод закэширует его и вернет.
     *
     * @param id айди объекта.
     * @return объект.
     */
    T get(String id);

    /**
     * Сохранить объект в хранилище.
     *
     * @param id     айди объекта.
     * @param unload инвалидировать его если true.
     */
    void save(String id, boolean unload);

    /**
     * Сохранить объект без инвалидации.
     *
     * @param id айди объекта.
     */
    default void save(String id) {
        save(id, false);
    }

    /**
     * Сохранить все объекты в хранилище.
     *
     * @param unload инвалидировать если true.
     */
    void saveAll(boolean unload);

    /**
     * Убрать объект из кэша.
     * @param id айди объекта.
     */
    void invalidate(String id);

}
