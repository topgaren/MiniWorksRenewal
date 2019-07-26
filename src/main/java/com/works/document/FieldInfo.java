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

    private String parameter;
    private String type;
    private String simpleType;
    private boolean required;
    private String description;

    private boolean list;   // 필드가 리스트 형태인지
    private boolean model;  // 타입이 DTO 객체인지
}
