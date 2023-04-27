package ua.olegsyrotyuk.athena.client;

import lombok.SneakyThrows;
import ua.olegsyrotyuk.athena.client.impl.local.LocalStorageManager;
import ua.olegsyrotyuk.athena.client.util.Athena;

public class Test {

    @SneakyThrows
    public static void main(String[] args) {
        LocalStorageManager manager = new LocalStorageManager();
        manager.connect("", "athena");
        Storage<User> users = manager.create("users", Athena.ATHENA_DEFAULT_GSON, User.class);
        users.getTopByStat("total_online", 15, true).forEach(System.out::println);
        manager.disconnect();
    }
}


