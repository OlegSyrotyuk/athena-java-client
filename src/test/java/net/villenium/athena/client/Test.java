package net.villenium.athena.client;

import lombok.SneakyThrows;
import net.villenium.athena.client.impl.StorageManager;
import net.villenium.athena.client.util.Operator;

public class Test {

    @SneakyThrows
    public static void main(String[] args) {
        StorageManager manager = new StorageManager();
        manager.start("localhost:2202", "client", "123456");
        IAthenaStorage<User> storage = manager.create("users", User.class);
        IReadOnlyObjectPool<User> objectPool = storage.newReadOnlyObjectPool();
        //objectPool.setDefaultObject(new User(null, 10, 10, 10));
        User larr4k = objectPool.get("Larr4k");
        System.out.println(larr4k);
        User user = objectPool.get("0xBukkitCoder");
        System.out.println(user);
        larr4k.setAge(19);
        //objectPool.save(larr4k.getName());
        manager.stop();

        storage.find()
                .whereEquals("rarity", "UNCOMMON")
                .whereAnd("min_physical_damage", 10, Operator.MORE_OR_EQUALS)
                .and(35, Operator.LESS_OR_EQUALS)
                .where("critical_chance", 21, Operator.MORE_OR_EQUALS)
                .count(15)
                .findAll();
    }

    //{"age":{"$gt":10,"$lt":19}}

}
