package com.tekion.arorapostgres.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
public class PostgresHikariConfig {
    
    @Value("${postgres.hikari.executor.pool.size.production:100}")
    private int productionPoolSize;
    
    @Value("${postgres.hikari.executor.pool.size.non-production:20}")
    private int nonProductionPoolSize;
    
    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Bean
    public ScheduledExecutorService hikariScheduledExecutorService() {
        boolean isProduction = activeProfile != null && activeProfile.contains("prod");
        int poolSize = isProduction ? productionPoolSize : nonProductionPoolSize;
        
        log.info("Creating HikariCP ScheduledExecutorService with pool size: {} (production: {})", 
                poolSize, isProduction);
        
        ThreadFactory threadFactory = new HikariThreadFactory("Hikari-Postgres-Housekeeper");
        
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
                poolSize,
                threadFactory,
                new ThreadPoolExecutor.DiscardPolicy()
        );

        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);


        executor.setRemoveOnCancelPolicy(true);
        
        return executor;
    }

    private static class HikariThreadFactory implements ThreadFactory {
        private final String namePrefix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        
        HikariThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }
        
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, namePrefix + "-" + threadNumber.getAndIncrement());
            thread.setDaemon(true);
            thread.setUncaughtExceptionHandler((t, e) ->
                log.error("Uncaught exception in HikariCP thread {}", t.getName(), e));
            return thread;
        }
    }
}

