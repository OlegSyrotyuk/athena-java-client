package net.villenium.athena.client.impl.strategy;

import com.google.gson.FieldNamingStrategy;
import net.villenium.athena.client.Id;

import java.lang.reflect.Field;

public class AthenaStorageStrategy implements FieldNamingStrategy {

    @Override
    public String translateName(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return "_id";
        }
        return field.getName();
    }
}
