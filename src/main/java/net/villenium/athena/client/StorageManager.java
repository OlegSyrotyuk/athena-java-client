package net.villenium.athena.client;

import com.google.gson.Gson;

public interface StorageManager {

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
     * @param gson сериализатор.
     * @param <T>  тип.
     * @return хранилище
     */
    <T> Storage<T> create(String name, Gson gson, Class<T> type);

}
