package com.company.erwmp.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisConfig {  //A class that stores Redis settings(Cache Settings Manual" for your system.)

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {  //That controlls caching

        RedisCacheConfiguration config =
                RedisCacheConfiguration.defaultCacheConfig() //Use default caching settings provided by Spring
                        .entryTtl(Duration.ofMinutes(10));  //Cached data expires automatically after 10 minutes.

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config) //Use the configuration we created earlie
                .build();  //Build the final cache manager
    }
}