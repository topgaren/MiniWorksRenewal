package com.works.document;

import com.works.annotation.DTO;
import com.works.annotation.DescriptionField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTOInfo {

    private String modelName;               // Package 경로를 포함한 DTO 이름.
    private String simpleModelName;         // Package 경로가 생략된 DTO 이름.
    private List<FieldInfo> fieldInfoList;  // DTO 내의 필드 정보를 저장할 리스트.
    private List<DTOInfo> nestedDTOList;    // 필드가 Nested DTO일 경우 해당 DTO 정보를 저장할 리스트.

    /**
     * DTOInfo 클래스의 생성자.
     *
     * @param dtoClass : 정보 파싱 대상 DTO 클래스.
     * @throws Exception
     */
    public DTOInfo(Class<?> dtoClass) throws Exception {

        modelName = dtoClass.getName();
        simpleModelName = dtoClass.getSimpleName();

        fieldInfoList = new ArrayList<>();

        int nestedDTOIndex = 0;

        for(Field field : dtoClass.getDeclaredFields()) {
            field.setAccessible(true);

            // VariableInfo 기본 필드 값 설정.
            FieldInfo fieldInfo = new FieldInfo(field);
            fieldInfoList.add(fieldInfo);
        }
    }
}
