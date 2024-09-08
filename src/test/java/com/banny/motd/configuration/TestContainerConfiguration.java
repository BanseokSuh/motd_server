package com.banny.motd.configuration;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainerConfiguration implements BeforeAllCallback, AfterAllCallback {

    private static final String REDIS_IMAGE = "redis:7.0.8-alpine"; // 컨테이너로 사용할 이미지
    private static final int REDIS_PORT = 6379;
    private GenericContainer<?> redis;

    @Override
    public void beforeAll(ExtensionContext context) { // 테스트 실행 전에 컨테이너를 실행
        redis = new GenericContainer(DockerImageName.parse(REDIS_IMAGE))
                .withExposedPorts(REDIS_PORT);

        redis.start(); // 컨테이너 실행

        System.setProperty("spring.redis.host", redis.getHost()); // 컨테이너의 호스트와 포트를 시스템 프로퍼티로 설정
        System.setProperty("spring.redis.port", String.valueOf(redis.getMappedPort(REDIS_PORT)));
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        if (redis != null) {
            redis.stop();
        }
    }
}
