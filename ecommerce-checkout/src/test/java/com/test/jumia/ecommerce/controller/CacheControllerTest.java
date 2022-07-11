package com.test.jumia.ecommerce.controller;

import com.test.jumia.ecommerce.service.CacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheControllerTest {

    @InjectMocks
    private CacheController controller;

    @Mock
    private CacheService service;

    @Test
    void shouldReturnCacheDataWhenCacheExists() {
        String key = "TEST_KEY";
        ConcurrentMap<Object, Object> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put(key, "VALUE1");

        when(service.getCache(key)).thenReturn(concurrentMap);

        ConcurrentMap<Object, Object> result = controller.getCache(key);

        assertNotNull(result);
        assertEquals("VALUE1", result.get(key));
    }

    @Test
    void shouldReturnEmptyDataWhenCAcheDoNotExists() {
        String key = "no-cache";

        when(service.getCache(key)).thenReturn(new ConcurrentHashMap<>());

        ConcurrentMap<Object, Object> result = controller.getCache(key);

        assertTrue(result.isEmpty());
        assertNull(result.get(key));
    }

    @Test
    void shouldCallClearCacheMethod() {
        String result = controller.clear("test");
        assertEquals("clear OK", result);
    }

    @Test
    void shouldCallClearAllCachesMethod() {
        String result = controller.clearAll();
        assertEquals("All caches cleared", result);
    }

    @Test
    void shouldReturnCacheNamesCorrectly() {
        Collection<String> cacheList = Arrays.asList("CACHE_1", "CACHE_2");

        when(service.getCacheNames()).thenReturn(cacheList);

        Collection<String> result = controller.getCacheNames();

        assertEquals(cacheList, result);
    }

    @Test
    void shouldReturnCacheSizeCorrectly() {
        String key = "TEST_KEY";

        when(service.cacheSize(key)).thenReturn(1);

        int result = controller.getCacheSize(key);

        assertEquals(1, result);
    }

    @Test
    void shouldReturn0WhenCacheIsNotPresent() {
        String key = "no-cache";

        when(service.cacheSize(key)).thenReturn(0);

        int result = controller.getCacheSize(key);

        assertEquals(0, result);
    }
}