package com.works.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * NullException 예외 핸들러
     *
     * @param  e : NullException 객체 참조변수
     * @return : 예외에 관한 정보를 담고 있는 AppError 객체
     */
    @ExceptionHandler(value = NullException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppError nullExceptionHandler(NullException e) {

        AppError appError = new AppError();
        appError.setStatus(404);
        appError.setError("Not Found");
        appError.setMessage(e.getMessage());

        return appError;
    }

    /**
     * BadRequestException 예외 핸들러
     *
     * @param e : BadRequestException 객체 참조변수
     * @return : 예외에 관한 정보를 담고 있는 AppError 객체
     */
    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError badRequestExceptionHandler(BadRequestException e) {

        AppError appError = new AppError();
        appError.setStatus(400);
        appError.setError("Bad Request");
        appError.setMessage(e.getMessage());

        return appError;
    }

    /**
     * ConflictException 예외 핸들러
     *
     * @param e : ConflictException 객체 참조변수
     * @return : 예외에 관한 정보를 담고 있는 AppError 객체
     */
    @ExceptionHandler(value = ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public AppError conflictExceptionHandler(ConflictException e) {

        AppError appError = new AppError();
        appError.setStatus(409);
        appError.setError("Conflict");
        appError.setMessage(e.getMessage());

        return appError;
    }
}
