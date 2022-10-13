package net.villenium.athena.client;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class DataOptions {

    /**
     * cache - кэшировать объект или нет.
     * cacheTime - время кэширования в секундах.
     * Базовые опции есть в Constant.DEFAULT_OPTIONS.
     */
    private final boolean cache;
    private final long cacheTime;

}