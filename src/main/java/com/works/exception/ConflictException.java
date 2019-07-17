package com.works.exception;

import lombok.NoArgsConstructor;

/**
 * ConflictException
 *
 * "409 Conflict"로 표현되는 예외를 나타내는 클래스
 */
@NoArgsConstructor
public class ConflictException extends Exception {

    public ConflictException(String errorMessage) {
        super(errorMessage);
    }
}
