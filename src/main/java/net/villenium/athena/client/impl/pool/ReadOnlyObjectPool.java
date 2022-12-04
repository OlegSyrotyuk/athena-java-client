package net.villenium.athena.client.impl.pool;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import net.villenium.athena.client.IAthenaStorage;
import net.villenium.athena.client.IReadOnlyObjectPool;
import net.villenium.athena.client.ObjectPool;

import javax.annotation.Nullable;
import java.util.Map;

@RequiredArgsConstructor
public class ReadOnlyObjectPool<T> implements IReadOnlyObjectPool<T> {

    private final Map<String, T> objectPool = Maps.newConcurrentMap();

    private final IAthenaStorage<T> storage;

    @Override
    public @Nullable T get(String id) {
        T object = objectPool.getOrDefault(id, null);
        if (object == null) {
            object = storage.find()
                    .whereEquals("_id", id)
                    .first();
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
