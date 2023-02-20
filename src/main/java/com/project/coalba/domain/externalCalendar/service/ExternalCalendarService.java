package com.project.coalba.domain.externalCalendar.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coalba.domain.auth.service.AuthSocialTokenService;
import com.project.coalba.domain.externalCalendar.dto.CalendarEventDto;
import com.project.coalba.domain.externalCalendar.dto.EventSearchFilter;
import com.project.coalba.domain.externalCalendar.dto.request.CalendarEventRequest;
import com.project.coalba.domain.externalCalendar.dto.CalendarPersonalDto;
import com.project.coalba.domain.externalCalendar.dto.response.GoogleCalendarEventsResponse;
import com.project.coalba.global.exception.BusinessException;
import com.project.coalba.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Service
@RequiredArgsConstructor
public class ExternalCalendarService {
    private static final String HTTP_REQUEST_CREATE = "https://www.googleapis.com/calendar/v3/calendars/{calendarId}/events";
    private static final String HTTP_REQUEST_DELETE = "https://www.googleapis.com/calendar/v3/calendars/{calendarId}/events/{eventId}";
    private static final String HTTP_REQUEST_GET = "https://www.googleapis.com/calendar/v3/calendars/{calendarId}/events?timeMax={timeMax}&timeMin={timeMin}&singleEvents={singleEvents}&maxResults={maxResults}&q={q}";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final AuthSocialTokenService authSocialTokenService;

    public void addEvent(CalendarPersonalDto calendarPersonalDto, CalendarEventDto calendarEventDto) {
        String accessToken = authSocialTokenService.updateAccessToken(calendarPersonalDto.getUserId());
        HttpHeaders headers = makeCalendarRequestHeader(accessToken);
        CalendarEventRequest event = new CalendarEventRequest(calendarEventDto);
        HttpEntity<String> request = new HttpEntity<String>(eventToJson(event), headers);
        Map uriVariable = Map.of(
                "calendarId", calendarPersonalDto.getCalendarId()
        );

        try {
            restTemplate.postForObject(HTTP_REQUEST_CREATE, request, HttpEntity.class, uriVariable);
        } catch (HttpClientErrorException e) {
            throw new BusinessException(ErrorCode.NOPERMISSION_TO_CALENDAR);
        }
    }

    public void deleteEvent(CalendarPersonalDto calendarPersonalDto, CalendarEventDto calendarEventDto) {
        GoogleCalendarEventsResponse eventsByFilter = getEventByFilter(calendarPersonalDto, calendarEventDto);
        if(eventsByFilter != null) {
            eventsByFilter.getItems().stream()
                    .forEach(event -> deleteEventByEventId(event.getId(), calendarPersonalDto));
        }
    }

    private GoogleCalendarEventsResponse getEventByFilter(CalendarPersonalDto calendarPersonalDto, CalendarEventDto calendarEventDto) {
        String accessToken = authSocialTokenService.updateAccessToken(calendarPersonalDto.getUserId());
        calendarPersonalDto.updateAccessToken(accessToken);
        HttpHeaders headers = makeCalendarRequestHeader(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        EventSearchFilter filter = new EventSearchFilter(calendarPersonalDto, calendarEventDto);
        Map uriVariable = objectMapper.convertValue(filter, Map.class);

        try {
            return (GoogleCalendarEventsResponse) restTemplate.exchange(HTTP_REQUEST_GET, HttpMethod.GET, request, GoogleCalendarEventsResponse.class, uriVariable).getBody();
        } catch (HttpClientErrorException e) {
            throw new BusinessException(ErrorCode.NOPERMISSION_TO_CALENDAR);
        }
    }

    private void deleteEventByEventId(String eventId, CalendarPersonalDto calendarPersonalDto) {
        HttpHeaders headers = makeCalendarRequestHeader(calendarPersonalDto.getAccessToken());
        HttpEntity<Void> request = new HttpEntity<>(headers);
        Map uriVariable = Map.of(
                "calendarId", calendarPersonalDto.getCalendarId(),
                "eventId", eventId
        );

        try {
            restTemplate.exchange(HTTP_REQUEST_DELETE, HttpMethod.DELETE, request, String.class, uriVariable);
        } catch (HttpClientErrorException e) {
            throw new BusinessException(ErrorCode.NOPERMISSION_TO_CALENDAR);
        }
    }

    private HttpHeaders makeCalendarRequestHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(accessToken);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Content-Type", APPLICATION_JSON_UTF8_VALUE);

        return headers;
    }

    private String eventToJson(CalendarEventRequest event) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
