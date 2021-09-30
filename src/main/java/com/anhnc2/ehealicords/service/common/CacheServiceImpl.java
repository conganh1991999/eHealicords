package com.anhnc2.ehealicords.service.common;

import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CacheServiceImpl implements CacheService {
    private final CacheManager cacheManager;

    @Override
    public void clearCache(String name) {
        Cache cache = cacheManager.getCache(name);

        if (cache != null) {
            cache.clear();
        }
    }
}
