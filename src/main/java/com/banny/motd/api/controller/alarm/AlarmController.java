package com.banny.motd.api.controller.alarm;

import com.banny.motd.api.service.alarm.AlarmService;
import com.banny.motd.domain.alarm.Alarm;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public ApiResponse<Page<Alarm>> getAlarmList(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return ApiResponse.ok(alarmService.getAlarmList(user.getId(), pageable));
    }

    @GetMapping("/subscribe")
    public SseEmitter subscribeAlarm(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return alarmService.subscribeAlarm(user.getId());
    }

}
