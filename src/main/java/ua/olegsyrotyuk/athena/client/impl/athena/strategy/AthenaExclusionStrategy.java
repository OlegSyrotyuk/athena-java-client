package ua.olegsyrotyuk.athena.client.impl.athena.strategy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import ua.olegsyrotyuk.athena.client.annotation.IgnoreField;

public class AthenaExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotations().stream().anyMatch(annotation -> annotation.annotationType() == IgnoreField.class);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
