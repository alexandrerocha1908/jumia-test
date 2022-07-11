package com.test.jumia.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    public ConcurrentMap<Object, Object> getCache(String key) {
        return Optional.ofNullable(getCacheValue(key))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElse(new ConcurrentHashMap<>());
    }

    public Integer cacheSize(String key) {
        return Optional.ofNullable(getCacheValue(key))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Map::size)
                .orElse(0);
    }

    public void clear(String key) {
        Optional.ofNullable(cacheManager.getCache(key))
                .ifPresent(Cache::clear);
    }

    public void clearAll() {
        cacheManager.getCacheNames().forEach(key -> Objects.requireNonNull(cacheManager.getCache(key)).clear());
    }

    public Collection<String> getCacheNames() {
        return cacheManager.getCacheNames();
    }

    private Optional<ConcurrentMap<Object, Object>> getCacheValue(String key) {
        return Optional.ofNullable(cacheManager.getCache(key))
                .map(c -> ((CaffeineCache) c).getNativeCache().asMap());
    }
}
