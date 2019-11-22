 package com.longge.common.service.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.longge.common.service.util.RedisUtils;

/**
 * @author roger yang
 * @date 10/31/2019
 */
@ConditionalOnBean(StringRedisTemplate.class)
public class RedisUtilsConfiguration {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Bean
    public RedisBootstrap redis() {
        RedisUtils.setRedisTemplate(stringRedisTemplate);
        return new RedisBootstrap();
    }
    
    static class RedisBootstrap {}
}
