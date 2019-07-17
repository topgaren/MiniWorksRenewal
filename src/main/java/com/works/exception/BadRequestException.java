package com.works.exception;

import lombok.NoArgsConstructor;

/**
 * BadRequestException
 *
 * "400 Bad Request"로 표현되는 예외를 나타내는 클래스
 */
@NoArgsConstructor
public class BadRequestException extends Exception {

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
