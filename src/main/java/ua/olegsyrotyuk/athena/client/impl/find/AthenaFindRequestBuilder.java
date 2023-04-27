package ua.olegsyrotyuk.athena.client.impl.find;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ua.olegsyrotyuk.athena.client.FindRequest;
import ua.olegsyrotyuk.athena.client.Storage;
import ua.olegsyrotyuk.athena.client.RequestBuilder;
import ua.olegsyrotyuk.athena.client.util.Athena;
import ua.olegsyrotyuk.athena.client.util.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class AthenaFindRequestBuilder<T> implements RequestBuilder<T> {

    private final Storage<T> storage;
    private final Map<String, Map<String, Object>> conditions = Maps.newHashMap();

    private RequestBuilder<T> builder;
    private int count = Integer.MAX_VALUE;

    @Override
    public RequestBuilder<T> where(String field, Object value, Operator operator) {
        Map<String, Object> values = conditions.getOrDefault(field, new HashMap<>());
        values.put(operator.getValue(), value);
        conditions.put(field, values);
        builder = this;
        return this;
    }

    @Override
    public AndRequest whereAnd(String field, Object value, Operator operator) {
        builder = this;
        return new AndRequest(field).and(value, operator);
    }

    @Override
    public RequestBuilder<T> count(int count) {
        this.count = count;
        builder = this;
        return this;
    }

    @Override
    public T first() {
        return findAll().stream().findFirst().orElse(null);
    }

    @Override
    public List<T> findAll() {
        return storage.findAll(new FindRequest(Athena.ATHENA_DEFAULT_GSON.toJson(conditions), count));
    }

    @AllArgsConstructor
    public class AndRequest implements IAndRequest<T> {
        private String field;

        public AndRequest and(Object value, Operator operator) {
            Map<String, Object> values = conditions.getOrDefault(field, new HashMap<>());
            values.put(operator.getValue(), value);
            conditions.put(field, values);
            return this;
        }

        public RequestBuilder<T> where(String field, Object value, Operator operator) {
            return (RequestBuilder<T>) builder.where(field, value, operator);
        }

        @Override
        public T first() {
            return builder.first();
        }

        @Override
        public List<T> findAll() {
            return builder.findAll();
        }
    }
}
