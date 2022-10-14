package net.villenium.athena.client.impl.find;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.villenium.athena.client.IAthenaStorage;
import net.villenium.athena.client.IFindRequestBuilder;
import net.villenium.athena.client.util.Constant;
import net.villenium.athena.client.util.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class FindRequestBuilder<T> implements IFindRequestBuilder {

    private final IAthenaStorage storage;
    private final Class<T> type;
    private final Map<String, Map<String, Object>> conditions = Maps.newHashMap();

    private FindRequestBuilder<T> builder;
    private int count = Integer.MAX_VALUE;

    @Override
    public IFindRequestBuilder where(String field, Object value, Operator operator) {
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
    public IFindRequestBuilder count(int count) {
        this.count = count;
        builder = this;
        return this;
    }

    @Override
    public List<T> execute() {
        return storage.findAll(new FindRequest(Constant.ATHENA_DEFAULT_GSON.toJson(conditions), count), type);
    }

    @AllArgsConstructor
    public class AndRequest implements IAndRequest {
        private String field;

        public AndRequest and(Object value, Operator operator) {
            Map<String, Object> values = conditions.getOrDefault(field, new HashMap<>());
            values.put(operator.getValue(), value);
            conditions.put(field, values);
            return this;
        }

        public FindRequestBuilder<T> where(String field, Object value, Operator operator) {
            return (FindRequestBuilder<T>) builder.where(field, value, operator);
        }
    }
}
