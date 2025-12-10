package com.learning.videocatalog.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ResponseErrorDTO(String message,
                               HttpStatus httpStatus,
                               LocalDateTime localDateTime) {
}
