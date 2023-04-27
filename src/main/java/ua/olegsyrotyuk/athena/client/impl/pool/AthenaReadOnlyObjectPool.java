package ua.olegsyrotyuk.athena.client.impl.pool;

import lombok.RequiredArgsConstructor;
import ua.olegsyrotyuk.athena.client.ReadOnlyObjectPool;
import ua.olegsyrotyuk.athena.client.Storage;

import javax.annotation.Nullable;
import java.util.Map;

@RequiredArgsConstructor
public class AthenaReadOnlyObjectPool<T> implements ReadOnlyObjectPool<T> {

    private final Storage<T> storage;
    private final Map<String, T> objectPool;

    @Override
    public @Nullable T get(String id) {
        T object = objectPool.getOrDefault(id, null);
        if (object == null) {
            object = storage.findById(id);
            if (object != null)
                objectPool.put(id, object);
        }
        return object;
    }

    @Override
    public void invalidate(String id) {
        objectPool.remove(id);
    }
}