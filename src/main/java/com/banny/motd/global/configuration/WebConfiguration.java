package com.banny.motd.global.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * CORS 설정 <p>
     * WebMvcConfigurer를 상속받아 addCorsMappings 메소드를 오버라이딩하여 CORS 설정을 추가 <p>
     *
     * @param registry CorsRegistry<p>
     *                 - allowedOrigins: 허용할 Origin<p>
     *                 - allowedMethods: 허용할 HTTP Method<p>
     *                 - allowedHeaders: 허용할 HTTP Header<p>
     *                 - allowCredentials: 쿠키 사용 여부<p>
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}
