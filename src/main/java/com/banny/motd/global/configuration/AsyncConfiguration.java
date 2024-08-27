package com.banny.motd.global.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    /**
     * 이메일 발송을 위한 비동기 처리를 위한 ThreadPoolTaskExecutor 빈 등록
     *
     * @return ThreadPoolTaskExecutor taskExecutor
     */
    @Bean(name = "messagePoolTaskExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor messageTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("message-");
        taskExecutor.initialize();

        return taskExecutor;
    }

}
