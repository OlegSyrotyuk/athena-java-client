package net.villenium.athena.client;

public interface IStorageManager {

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
     * Получить хранилище.
     *
     * @param name имя хранилища.
     * @param type тип объектов хранилища.
     * @param <T>  тип.
     * @return хранилище
     */
    <T> IAthenaStorage<T> create(String name, Class<T> type);

}
