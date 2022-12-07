package net.villenium.athena.client.impl.pool;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.villenium.athena.client.IAthenaStorage;
import net.villenium.athena.client.ObjectPool;
import net.villenium.athena.client.annotation.Id;
import net.villenium.athena.client.util.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

@RequiredArgsConstructor
public class AthenaObjectPool<T> implements ObjectPool<T> {

    private final Map<String, T> objectPool = Maps.newConcurrentMap();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final IAthenaStorage<T> storage;
    private T DEFAULT_OBJECT;

    @Override
    public void setDefaultObject(T object) {
        DEFAULT_OBJECT = object;
    }

    @SneakyThrows
    @Override
    public T get(String id) {
        T object = objectPool.getOrDefault(id, null);
        if (DEFAULT_OBJECT != null) {
            if (object == null) {
                T first = storage.findById(id);
                System.out.println(first);
                if (first != null) {
                    object = first;
                } else {
                    for (Field field : DEFAULT_OBJECT.getClass().getDeclaredFields()) {
                        if (field.isAnnotationPresent(Id.class)) {
                            field.setAccessible(true);
                            field.set(DEFAULT_OBJECT, id);
                            field.setAccessible(false);
                        }
                    }
                    object = DEFAULT_OBJECT;
                    storage.upsert(id, DEFAULT_OBJECT);
                }
                objectPool.put(id, object);
            }
        } else {
            logger.error("Default object for this pool isn't set! Fix it!");
        }
        return object;
    }

    @Override
    public void save(String id, boolean unload) {
        storage.upsert(id, get(id));
        if (unload)
            objectPool.remove(id);
    }

    @Override
    public void saveAll(boolean unload) {
        objectPool.keySet().forEach(object -> {
            save(object, unload);
        });
    }
}
