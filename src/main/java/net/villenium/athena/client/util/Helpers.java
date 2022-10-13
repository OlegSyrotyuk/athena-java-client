package net.villenium.athena.client.util;

import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.subscription.MultiEmitter;
import lombok.var;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Helpers {

    public static <T> T unaryCall(Supplier<T> supplier) {
        return supplier.get();
    }

    /**
     * Связывание асинхронного Unary-вызова с {@link CompletableFuture}.
     *
     * @param callPerformer Асинхронный Unary-вызов.
     * @param <T>           Тип результата вызова.
     * @return {@link CompletableFuture} с результатом вызова.
     */
    public static <T> CompletableFuture<T> unaryAsyncCall(Consumer<StreamObserver<T>> callPerformer) {
        var cf = new CompletableFuture<T>();
        callPerformer.accept(mkStreamObserverWithFuture(cf));
        return cf;
    }


    /**
     * Создание StreamObserver, который связывает свой результат с CompletableFuture.
     * <p>
     * Только для Unary-вызовов!
     */
    private static <T> StreamObserver<T> mkStreamObserverWithFuture(CompletableFuture<T> cf) {
        return new StreamObserver<T>() {
            @Override
            public void onNext(T value) {
                cf.complete(value);
            }

            @Override
            public void onError(Throwable t) {
                cf.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {
            }
        };
    }

    /**
     * Связывание {@link MultiEmitter} со {@link StreamObserver}.
     *
     * @param emitter Экземпляр {@link MultiEmitter}.
     * @param <T>     Тип оперируемый {@link MultiEmitter}.
     * @return Связанный {@link StreamObserver}.
     */
    public static <T> StreamObserver<T> wrapEmitterWithStreamObserver(MultiEmitter<? super T> emitter) {
        return new StreamObserver<T>() {
            @Override
            public void onNext(T value) {
                emitter.emit(value);
            }

            @Override
            public void onError(Throwable t) {
                emitter.fail(t);
            }

            @Override
            public void onCompleted() {
                emitter.complete();
            }
        };
    }
}
