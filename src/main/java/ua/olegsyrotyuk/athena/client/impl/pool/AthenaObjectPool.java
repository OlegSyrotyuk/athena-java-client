package ua.olegsyrotyuk.athena.client.impl.pool;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import ua.olegsyrotyuk.athena.client.ObjectPool;
import ua.olegsyrotyuk.athena.client.Storage;
import ua.olegsyrotyuk.athena.client.annotation.Id;
import ua.olegsyrotyuk.athena.client.util.Athena;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class AthenaObjectPool<T> implements ObjectPool<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Storage<T> storage;
    private final Map<String, T> objectPool;
    private T DEFAULT_OBJECT;
    @Getter
    private boolean autoSave = true;
    @Getter
    private long autoSaveTime = 10L;
    @Getter
    @Setter
    private boolean debug;

    {
        if (isAutoSave()) {
            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> saveAll(false), autoSaveTime, autoSaveTime, TimeUnit.MINUTES);
        }
    }

    @Override
    public void setDefaultObject(T object) {
        DEFAULT_OBJECT = object;
    }

    @Override
    public void setAutoSave(boolean value) {
        autoSave = value;
    }

    @Override
    public void setAutoSaveTime(long value) {
        autoSaveTime = value;
    }

    @SneakyThrows
    @Override
    public T get(String id) {
        T object = objectPool.getOrDefault(id, null);
        if (DEFAULT_OBJECT != null) {
            if (object == null) {
                T first = storage.findById(id);
                if (first != null) {
                    object = first;
                } else {
                    object = Athena.ATHENA_DEFAULT_GSON.fromJson(Athena.ATHENA_DEFAULT_GSON.toJson(DEFAULT_OBJECT), storage.getType());
                    for (Field field : object.getClass().getDeclaredFields()) {
                        if (field.isAnnotationPresent(Id.class)) {
                            field.setAccessible(true);
                            field.set(object, id);
                            field.setAccessible(false);
                        }
                    }
                    storage.upsert(id, object);
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
        T object = get(id);
        debug("Saving object with id " + id);
        debug(object.toString());
        storage.upsert(id, object);
        debug("Object with id " + id + " saved.");
        debug(object.toString());
        if (unload) {
            debug("Invalidate object with id " + id);
            objectPool.remove(id);
            debug("Object with id " + id + " invalidated");
        }
    }

    @Override
    public void saveAll(boolean unload) {
        debug("Saving objects...");
        objectPool.keySet().forEach(object -> {
            debug(objectPool.get(object).toString());
            save(object, unload);
        });
        debug("All objects have been saved.");
    }

    @Override
    public void invalidate(String id) {
        objectPool.remove(id);
    }

    private void debug(String message, Object... args) {
        if (debug) {
            System.out.println("Debug: " + String.format(message, args));
        }
    }
}
