package net.villenium.athena.client;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Необходимо аннотировать все айди-поля объектов.
 * Название поля с этой аннотацией заменяется на "_id".
 * Необходимо для корректной работы сервиса.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
}
