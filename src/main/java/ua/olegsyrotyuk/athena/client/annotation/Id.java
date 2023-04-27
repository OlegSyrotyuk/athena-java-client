package ua.olegsyrotyuk.athena.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Необходимо аннотировать все айди-поля объектов.
 * Название поля с этой аннотацией заменяется на "_id".
 * Необходимо для корректной работы сервиса.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
}
