package net.villenium.athena.client;

import javax.annotation.Nullable;

public interface IReadOnlyObjectPool<T> {

    /**
     * Получить объект из кэша.
     * Если объект отсутствует, метод закэширует его и вернет.
     *
     * @param id айди объекта.
     * @return объект.
     */
    @Nullable T get(String id);

}
