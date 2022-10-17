package net.villenium.athena.client;

public interface ObjectPool<T> {

    /**
     * Установить базовый объект пула.
     * @param object экземпляр объекта с базовыми значениями.
     */
    void setDefaultObject(T object);

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

}
