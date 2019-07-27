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
        nestedDTOList = new ArrayList<>();

        int nestedDTOIndex = 0;

        for(Field field : dtoClass.getDeclaredFields()) {
            field.setAccessible(true);

            // VariableInfo 기본 필드 값 설정.
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setParameter(field.getName());
            fieldInfo.setType(field.getType().getName());
            fieldInfo.setSimpleType(field.getType().getSimpleName());
            fieldInfo.setDescription(field.getAnnotation(DescriptionField.class).description());

            // 필드가 리스트(List)인 경우
            if(fieldInfo.getSimpleType().equals("List")) {
                fieldInfo.setList(true);

                // 리스트 내의 타입을 추가적으로 확인.
                ParameterizedType pt = (ParameterizedType)field.getGenericType();
                Class<?> classInList = (Class<?>)pt.getActualTypeArguments()[0];
                fieldInfo.setType(classInList.getName());
            }

            // 필드 타입이 또 다른 DTO인지 확인.
            if(!field.getType().isPrimitive()) {
                Class<?> nestedObject = Class.forName(fieldInfo.getType());
                if(nestedObject.isAnnotationPresent(DTO.class)) {
                    fieldInfo.setModel(true);
                    fieldInfo.setNestedDTOIndex(nestedDTOIndex);
                    nestedDTOList.add(new DTOInfo(nestedObject));
                    nestedDTOIndex++;
                }
            }

            fieldInfoList.add(fieldInfo);
        }
    }
}
