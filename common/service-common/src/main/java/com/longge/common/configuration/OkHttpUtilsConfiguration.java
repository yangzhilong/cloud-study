 package com.longge.common.configuration;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.longge.common.configuration.OkHttpUtilsConfiguration.OkHttpConfig;
import com.longge.common.util.OkHttpUtils;

import lombok.Getter;
import lombok.Setter;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * @author roger yang
 * @date 9/16/2019
 */
@EnableConfigurationProperties(value = {OkHttpConfig.class})
@ConditionalOnClass(value = {OkHttpClient.class})
public class OkHttpUtilsConfiguration {
    @Resource
    private OkHttpConfig okHttpConfig;
    
    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectionPool(pool())
                .connectTimeout(okHttpConfig.getConnectTimeoutMs(), TimeUnit.MILLISECONDS)
                .readTimeout(okHttpConfig.getReadTimeoutMs(), TimeUnit.MILLISECONDS)
                .writeTimeout(okHttpConfig.getWriteTimeoutMs(),TimeUnit.MILLISECONDS)
                .build();
        
        OkHttpUtils.setOkHttpClient(client);
        return client;
    }
    
    @Bean
    public ConnectionPool pool() {
        return new ConnectionPool(okHttpConfig.getMaxIdle(), okHttpConfig.getKeepAliveDurationSec(), TimeUnit.SECONDS);
    }
    
    @Component
    @ConfigurationProperties(prefix = "okhttp")
    @Getter
    @Setter
    @Validated
    static class OkHttpConfig {
        @NotNull
        private Long connectTimeoutMs;
        @NotNull
        private Long readTimeoutMs;
        @NotNull
        private Long writeTimeoutMs;
        @NotNull
        private Integer maxIdle;
        @NotNull
        private Long keepAliveDurationSec;
    }
}
