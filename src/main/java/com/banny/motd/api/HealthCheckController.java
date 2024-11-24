package com.banny.motd.api;

import com.banny.motd.global.dto.response.ApiResponse;
import com.banny.motd.global.dto.response.ApiStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class HealthCheckController {

    @GetMapping
    public ApiResponse<String> healthCheck() {
        return ApiResponse.of(ApiStatus.success(), "health check success");
    }

}
