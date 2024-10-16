package com.banny.motd.global.helper;

import com.banny.motd.domain.alarm.AlarmEvent;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.StatusType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Deprecated
@Slf4j
public class SerializeHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error serializing object to JSON: {}", e.getMessage());
            throw new ApplicationException(StatusType.FAIL_SERVER_ERROR, "Serializing Error");
        }
    }

    public static AlarmEvent deserialize(String json, Class<AlarmEvent> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing object from JSON: {}", e.getMessage());
            throw new ApplicationException(StatusType.FAIL_SERVER_ERROR, "Deserializing Error");
        }
    }
}
