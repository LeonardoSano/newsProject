package com.nyTimes.configurations;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class JedisConfig {

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();

		redisConfig.setHostName("localhost");
		redisConfig.setPort(6379);
		
		JedisClientConfigurationBuilder jedisConfig = JedisClientConfiguration.builder();
		jedisConfig.connectTimeout(Duration.ofMinutes(5));
		return new JedisConnectionFactory(redisConfig,jedisConfig.build());
	}

	@Bean
	public CacheManager cacheManager() {
		RedisCacheManagerBuilder builder = RedisCacheManagerBuilder.fromConnectionFactory(jedisConnectionFactory());
		List<String> cacheNames = Arrays.asList("news","hackNews","nyNews");
		builder.initialCacheNames(new HashSet<String>(cacheNames));
		return builder.build();
	}
	
}
