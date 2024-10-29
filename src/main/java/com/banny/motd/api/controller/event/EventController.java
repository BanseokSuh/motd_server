package com.banny.motd.api.controller.event;

import com.banny.motd.api.controller.event.request.EventCreateRequest;
import com.banny.motd.api.service.event.EventService;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
@RestController
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ApiResponse<Void> createEvent(@Valid @RequestBody EventCreateRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        eventService.createEvent(request.toServiceRequest(), user.getId());

        return ApiResponse.ok();
    }

}
