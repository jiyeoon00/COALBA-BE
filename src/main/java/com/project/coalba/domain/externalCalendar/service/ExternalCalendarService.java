package com.project.coalba.domain.externalCalendar.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coalba.domain.externalCalendar.dto.CalendarEventDto;
import com.project.coalba.domain.externalCalendar.dto.request.CalendarEventRequest;
import com.project.coalba.domain.externalCalendar.dto.CalendarPersonalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

    private final RestTemplate restTemplate;

    public void addEvent(CalendarPersonalDto calendarPersonalDto, CalendarEventDto calendarEventDto) {
        HttpHeaders headers = makeCalendarRequestHeader(calendarPersonalDto.getAccessToken());
        CalendarEventRequest event = new CalendarEventRequest(calendarEventDto);
        HttpEntity<String> request = new HttpEntity<String>(eventToJson(event), headers);
        Map uriVariable = Map.of(
                "calendarId", calendarPersonalDto.getCalendarId()
        );

        try {
            restTemplate.postForObject(HTTP_REQUEST_CREATE, request, HttpEntity.class, uriVariable);
        } catch (HttpClientErrorException e) {
                System.out.println("해당 캘린더에 접근할 권한이 없습니다.");
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
            /**
             * 접근 권한 문제일 경우 처리해주기
             */
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
