package com.banny.motd.api.controller.alarm;

import com.banny.motd.api.service.alarm.AlarmService;
import com.banny.motd.api.service.alarm.response.AlarmListServiceResponse;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public ApiResponse<List<AlarmListServiceResponse>> getAlarmList(Authentication authentication, SearchRequest request) {
        User user = (User) authentication.getPrincipal();
        List<AlarmListServiceResponse> alarmList = alarmService.getAlarmList(user.getId(), request);
        return ApiResponse.ok(alarmList);
    }

    @GetMapping("/subscribe")
    public SseEmitter subscribeAlarm(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return alarmService.subscribeAlarm(user.getId());
    }

}
