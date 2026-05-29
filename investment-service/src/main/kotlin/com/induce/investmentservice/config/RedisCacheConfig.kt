package com.induce.investmentservice.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableCaching
class RedisCacheConfig {

    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): RedisCacheManager {
        val jacksonSerializer = GenericJacksonJsonRedisSerializer.create { builder ->
            builder
                .enableSpringCacheNullValueSupport()
                .enableUnsafeDefaultTyping()
        }

        val defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .disableCachingNullValues()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer())
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer)
            )

        val initialCacheConfigurations = mapOf(
            "inflationRate" to defaultCacheConfig.entryTtl(Duration.ofHours(12)),
            "bondHistory" to defaultCacheConfig.entryTtl(Duration.ofMinutes(30)),
            "depositHistory" to defaultCacheConfig.entryTtl(Duration.ofMinutes(30)),
            "stockHistory" to defaultCacheConfig.entryTtl(Duration.ofMinutes(30))
        )

        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(defaultCacheConfig)
            .withInitialCacheConfigurations(initialCacheConfigurations)
            .build()
    }
}
