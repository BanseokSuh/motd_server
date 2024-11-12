package com.banny.motd.api.controller.event;

import com.banny.motd.api.controller.event.request.EventCreateRequest;
import com.banny.motd.api.controller.event.response.EventCreateResponse;
import com.banny.motd.api.controller.event.response.ParticipationCreateResponse;
import com.banny.motd.api.service.event.EventService;
import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.participation.Participation;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
@RestController
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ApiResponse<EventCreateResponse> createEvent(@Valid @RequestBody EventCreateRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Event event = eventService.createEvent(request.toServiceRequest(), user.getId());

        return ApiResponse.ok(EventCreateResponse.from(event));
    }

    @PostMapping("/{eventId}/participate")
    public ApiResponse<ParticipationCreateResponse> participateEvent(@PathVariable Long eventId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Participation participation = eventService.participateEvent(eventId, user.getId(), LocalDateTime.now());

        return ApiResponse.ok(ParticipationCreateResponse.from(participation));
    }

}
