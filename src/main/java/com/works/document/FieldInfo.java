package com.works.document;

import com.works.annotation.DTO;
import com.works.annotation.DescriptionField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

@AllArgsConstructor
@Getter
@Setter
public class FieldInfo {

    private String parameter;   // Document 테이블에 표기될 이름
    private String type;        // Package 경로를 포함한 Java 타입
    private String simpleType;  // Package 경로가 생략된 Java 타입
    private boolean required;   // 요청 시 해당 필드의 필수 여부
    private String description; // 필드에 대한 설명

    private boolean list;       // 필드가 리스트 형태인지
    private boolean model;      // 타입이 DTO 객체인지


    /**
     *  FieldInfo 클래스의 생성자.
     */
    public FieldInfo() {

        parameter = "";
        type = "";
        simpleType = "";
        required = false;
        description = "";
        list = false;
        model = false;
    }

    public FieldInfo(Field field) throws Exception {

        this();
        parameter = field.getName();
        type = field.getType().getName();
        simpleType = field.getType().getSimpleName();
        description = field.getAnnotation(DescriptionField.class).description();

        // 필드가 리스트(List)인 경우
        if(simpleType.equals("List")) {
            list = true;
            simpleType = "List<" + simpleType + ">";

            // 리스트 내의 타입을 추가적으로 확인.
            ParameterizedType pt = (ParameterizedType)field.getGenericType();
            Class<?> classInList = (Class<?>)pt.getActualTypeArguments()[0];
            type = classInList.getName();
        }

        // 필드 타입이 또 다른 DTO인지 확인.
        if(!field.getType().isPrimitive()) {
            Class<?> nestedObject = Class.forName(type);
            if(nestedObject.isAnnotationPresent(DTO.class)) {
                model = true;
            }
        }
    }
}
