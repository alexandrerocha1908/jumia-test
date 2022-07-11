package com.test.jumia.ecommerce.controller;

import com.test.jumia.ecommerce.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheService cacheService;

    @GetMapping(value = "/{key}")
    public ConcurrentMap<Object, Object> getCache(@PathVariable String key) {
        return cacheService.getCache(key);
    }

    @GetMapping(value = "/{key}/size")
    public Integer getCacheSize(@PathVariable String key) {
        return cacheService.cacheSize(key);
    }

    @PutMapping
    public String clearAll() {
        cacheService.clearAll();
        return "All caches cleared";
    }

    @PutMapping(value = "/{key}")
    public String clear(@PathVariable String key) {
        cacheService.clear(key);
        return "clear OK";
    }

    @GetMapping
    public Collection<String> getCacheNames() {
        return cacheService.getCacheNames();
    }

}
