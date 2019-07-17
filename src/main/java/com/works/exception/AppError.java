package com.works.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AppError {

    // 에러가 발생한 시간을 저장.
    private Date timeStamp;

    // HTTP Status 값을 저장. (Example: 404)
    private int status;

    // HTTP Status에 해당하는 에러를 저장. (Example: Not Found)
    private String error;

    // 에러가 발생 원인 등의 내용을 저장.
    private String message;

    public AppError() {
        timeStamp = new Date();
    }
}
