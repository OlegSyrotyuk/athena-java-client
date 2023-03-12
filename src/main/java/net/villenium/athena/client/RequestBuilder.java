package net.villenium.athena.client;

import net.villenium.athena.client.util.Operator;

import java.util.List;

/**
 * Построение запросов поиска для хранилища.
 * Использовать where можно множество раз, тем самым позволяя использовать гибкий поиск.
 * Объекты возвращаемые этим запросом НЕ кэшируются.
 */
public interface RequestBuilder<T> {

    /**
     * Поиск по значению поля.
     *
     * @param field    поле.
     * @param value    значение.
     * @param operator оператор сравнения. (для текстовых значений использовать только EQUALS и NOT_EQUALS).
     * @return этот билдер.
     * Например: where("age", 10, Operator.LESS_OR_EQUALS) вернет всех чей возраст меньше или равен десяти.
     */
    RequestBuilder<T> where(String field, Object value, Operator operator);

    /**
     * Сокращенный вызов оператора равно.
     *
     * @param field поле.
     * @param value значение.
     * @return этот билдер.
     */
    default RequestBuilder<T> whereEquals(String field, Object value) {
        return where(field, value, Operator.EQUALS);
    }

    /**
     * Сокращенный вызов оператора не равно.
     *
     * @param field поле.
     * @param value значение.
     * @return этот билдер.
     */
    default RequestBuilder<T> whereNotEquals(String field, Object value) {
        return where(field, value, Operator.NOT_EQUALS);
    }

    /**
     * Поиск значения по полю с возможностью добавить для значения несколько условий.
     * К примеру findAll().whereAnd("age", 10, Operator.MORE_OR_EQUALS).and(18, Operator.LESS_OR_EQUALS)
     * Вернет всех пользователей чей возраст больше или равен десяти, но при этом не более чем восемнадцать.
     *
     * @param field    поле.
     * @param value    значение.
     * @param operator оператор сравнения. (для текстовых значений использовать только EQUALS и NOT_EQUALS).
     * @return этот билдер.
     */
    IAndRequest<T> whereAnd(String field, Object value, Operator operator);

    /**
     * Сокращенный вызов оператора равно с возможностью добавить несколько условий.
     *
     * @param field поле.
     * @param value значение.
     * @return этот билдер.
     */
    default IAndRequest<T> whereAndEquals(String field, Object value) {
        return whereAnd(field, value, Operator.EQUALS);
    }

    /**
     * Сокращенный вызов оператора не равно с возможностью добавить несколько условий.
     *
     * @param field поле.
     * @param value значение.
     * @return этот билдер.
     */
    default IAndRequest<T> whereAndNotEquals(String field, Object value) {
        return whereAnd(field, value, Operator.NOT_EQUALS);
    }

    /**
     * Установить желаемое количество полученных объектов.
     *
     * @param count количество.
     * @return этот билдер.
     */
    RequestBuilder<T> count(int count);

    /**
     * Получить первый подходящий ответ.
     *
     * @return объект.
     */
    T first();

    /**
     * Получить все подходящие объекты.
     *
     * @return список объектов.
     */
    List<T> findAll();

    interface IAndRequest<T> {

        /**
         * Описание в whereAnd
         *
         * @param value    значение.
         * @param operator оператор сравнивания.
         * @return этот билдер.
         */
        IAndRequest<T> and(Object value, Operator operator);

        /**
         * Поиск по значению поля с выходом к IFindRequestBuilder.
         *
         * @param field    поле.
         * @param value    значение.
         * @param operator оператор сравнивания.
         * @return билдер запроса.
         */
        RequestBuilder<T> where(String field, Object value, Operator operator);

        /**
         * Получить первый подходящий ответ.
         *
         * @return объект.
         */
        T first();

        /**
         * Получить все подходящие объекты.
         *
         * @return список объектов.
         */
        List<T> findAll();

    }

}
