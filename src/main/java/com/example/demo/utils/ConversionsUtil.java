package com.example.demo.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ConversionsUtil {
    public LocalDateTime convertStringToLocalDateTime(String stringDateTime) {
        // Define the expected format of the string representation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Parse the string into a LocalDateTime object using the defined formatter
        LocalDateTime localDateTime = LocalDateTime.parse(stringDateTime, formatter);

        return localDateTime;
    }
}
