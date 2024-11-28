package com.banny.motd.api.controller.event;

import com.banny.motd.api.controller.event.request.EventCreateRequest;
import com.banny.motd.api.controller.event.response.EventCreateResponse;
import com.banny.motd.api.controller.event.response.EventDetailResponse;
import com.banny.motd.api.controller.event.response.EventListResponse;
import com.banny.motd.api.controller.event.response.ParticipationCreateResponse;
import com.banny.motd.api.service.event.EventService;
import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.EventDetail;
import com.banny.motd.domain.participation.Participation;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.global.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
@RestController
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ApiResponse<List<EventListResponse>> getEventList(SearchRequest request) {
        List<Event> events = eventService.getEventList(request);
        List<EventListResponse> eventListResponses = events.stream().map(EventListResponse::from).toList();

        return ApiResponse.ok(eventListResponses);
    }

    @GetMapping("/{id}")
    public ApiResponse<EventDetailResponse> getEvent(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        EventDetail event = eventService.getEvent(id, user.getId());

        return ApiResponse.ok(EventDetailResponse.from(event));
    }

    @PostMapping
    public ApiResponse<EventCreateResponse> createEvent(@Valid @RequestBody EventCreateRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Event event = eventService.createEvent(request.toServiceRequest(), user.getId());

        return ApiResponse.ok(EventCreateResponse.from(event));
    }

    @PostMapping("/{id}/participate")
    public ApiResponse<ParticipationCreateResponse> participateEvent(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Participation participation = eventService.participateEvent(id, user.getId(), LocalDateTime.now());

        return ApiResponse.ok(ParticipationCreateResponse.from(participation));
    }

    @PostMapping("/{id}/participate/cancel")
    public ApiResponse<Void> cancelParticipateEvent(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        eventService.cancelParticipateEvent(id, user.getId());

        return ApiResponse.ok();
    }

}
