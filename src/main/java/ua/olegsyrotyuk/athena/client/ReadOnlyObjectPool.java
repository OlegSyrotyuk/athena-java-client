package ua.olegsyrotyuk.athena.client;

import javax.annotation.Nullable;

public interface ReadOnlyObjectPool<T> {

    /**
     * Получить объект из кэша.
     * Если объект отсутствует, метод закэширует его и вернет.
     *
     * @param id айди объекта.
     * @return объект.
     */
    @Nullable T get(String id);

    /**
     * Убрать объект из кэша.
     * @param id айди объекта.
     */
    void invalidate(String id);

}
