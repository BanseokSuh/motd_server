package com.banny.motd.domain.alarm.api;

import com.banny.motd.domain.alarm.application.AlarmService;
import com.banny.motd.domain.alarm.domain.Alarm;
import com.banny.motd.domain.user.domain.User;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.ResultType;
import com.banny.motd.global.dto.response.Response;
import com.banny.motd.global.util.ClassUtils;
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
    public Response<Page<Alarm>> getAlarmList(Authentication authentication, Pageable pageable) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class).orElseThrow(
                () -> new ApplicationException(ResultType.FAIL_SERVER_ERROR, "Casting to User class failed."));

        return Response.success(alarmService.getAlarmList(user.getId(), pageable));
    }

    @GetMapping("/subscribe")
    public SseEmitter subscribeAlarm(Authentication authentication) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class).orElseThrow(
                () -> new ApplicationException(ResultType.FAIL_SERVER_ERROR, "Casting to User class failed."));

        return alarmService.subscribeAlarm(user.getId());
    }
}
