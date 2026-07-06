package com.notification.api.dao.impl;

import com.notification.api.dao.interfaces.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

import static com.notification.api.constants.ApplicationConstant.TEMPLATE_REDIS_PREFIX;

@Service
@Slf4j
@RequiredArgsConstructor
class CacheServiceImpl implements CacheService {
    private final RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public <T> Optional<T> getById(String tenantId, String id, Class<T> clazz) {
        return get(tenantId, "BY_ID:".concat(id), clazz);
    }

    @Override
    public <T> Optional<T> getByName(String tenantId, String name, Class<T> clazz) {
        return get(tenantId, "BY_NAME:".concat(name), clazz);
    }

    @Override
    public <T> void deleteById(String tenantId, String id) {
        delete(tenantId, "BY_ID:".concat(id));
    }

    @Override
    public <T> void deleteByName(String tenantId, String name) {
        delete(tenantId, "BY_NAME:".concat(name));
    }

    @Override
    public <T> void putById(String tenantId, String id, T value) {
        put(tenantId, "BY_ID:".concat(id), value);
    }

    @Override
    public <T> void putByName(String tenantId, String name, T value) {
        put(tenantId, "BY_NAME:".concat(name), value);
    }

    private <T> void put(String tenantId, String hashkey, T data) {
        String json = objectMapper.writeValueAsString(data);
        redisTemplate.opsForHash().put(getTenantCacheKey(tenantId), hashkey, json);
    }

    private <T> Optional<T> get(String tenantId, String hashkey, Class<T> clazz) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        String jsonData = ops.get(getTenantCacheKey(tenantId), hashkey);
        if (jsonData == null) {
            return Optional.empty();
        }
        return Optional.of(objectMapper.readValue(jsonData, clazz));
    }

    private void delete(String tenantId, String hashkey) {
        redisTemplate.opsForHash().delete(getTenantCacheKey(tenantId), hashkey);
    }

    private String getTenantCacheKey(String tenantId) {
        return TEMPLATE_REDIS_PREFIX.concat(tenantId);

    }
}
