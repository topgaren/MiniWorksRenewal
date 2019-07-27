package com.works.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldInfo {

    private String parameter;   // Document 테이블에 표기될 이름
    private String fieldName;   // 필드의 본래 변수명
    private String type;        // Package 경로를 포함한 Java 타입
    private String simpleType;  // Package 경로가 생략된 Java 타입
    private boolean required;   // 요청 시 해당 필드의 필수 여부
    private String description; // 필드에 대한 설명

    private boolean list;   // 필드가 리스트 형태인지
    private boolean model;  // 타입이 DTO 객체인지
}
