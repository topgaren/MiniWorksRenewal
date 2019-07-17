package com.works.exception;

import lombok.NoArgsConstructor;

/**
 * NullException
 *
 * "404 Not Found"로 표현되는 예외를 나타내는 클래스
 */
@NoArgsConstructor
public class NullException extends Exception {

    public NullException(String errorMessage) {
        super(errorMessage);
    }
}
