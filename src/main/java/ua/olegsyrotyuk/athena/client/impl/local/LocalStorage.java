package ua.olegsyrotyuk.athena.client.impl.local;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.RequiredArgsConstructor;
import ua.olegsyrotyuk.athena.client.impl.find.AthenaFindRequestBuilder;
import ua.olegsyrotyuk.athena.client.impl.pool.AthenaObjectPool;
import ua.olegsyrotyuk.athena.client.impl.pool.AthenaReadOnlyObjectPool;
import org.bson.BsonRegularExpression;
import org.bson.Document;
import ua.olegsyrotyuk.athena.client.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class LocalStorage<T> implements Storage<T> {

    private final MongoCollection<Document> storage;
    private final Gson gson;
    private final Class<T> type;

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public void upsert(String id, Object data, DataOptions options) {
        Document document = gson.fromJson(gson.toJson(data), Document.class);
        document.remove("_id");
        storage.replaceOne(Filters.eq("_id", id), document, new ReplaceOptions().upsert(true));
    }

    @Override
    public List<T> getTopByStat(String stat, int count, boolean ascending) {
        List<T> list = Lists.newArrayList();
        for (Document document : storage.find().sort(new BasicDBObject(stat, ascending ? -1 : 1)).limit(count)) {
            list.add(gson.fromJson(gson.toJson(document), type));
        }
        return list;
    }

    @Override
    public List<T> findAll(FindRequest request) {
        List<T> list = Lists.newArrayList();
        for (Document document : storage.find(gson.fromJson(request.getConditions(), Document.class)).limit(request.getCount())) {
            list.add(gson.fromJson(gson.toJson(document), type));
        }
        return list;
    }

    @Override
    public RequestBuilder<T> find() {
        return new AthenaFindRequestBuilder<>(this);
    }

    @Override
    public T findById(String id) {
        return gson.fromJson(gson.toJson(storage.find(new Document("_id", new BsonRegularExpression(String.format("^%s$", id), "i"))).first()), type);
    }

    @Override
    public ObjectPool<T> newObjectPool(Map<String, T> source) {
        return new AthenaObjectPool<T>(this, source);
    }

    @Override
    public ReadOnlyObjectPool<T> newReadOnlyObjectPool(Map<String, T> source) {
        return new AthenaReadOnlyObjectPool<T>(this, source);
    }
}
