package com.works.document;

import com.works.annotation.DTO;
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

    private String modelName;
    private String simpleModelName;
    private List<VariableInfo> variableInfoList;

    /**
     * DTOInfo의 생성자.
     *
     * @param dtoClass : 정보 파싱 대상 DTO 클래스.
     * @throws Exception
     */
    public DTOInfo(Class<?> dtoClass) throws Exception {

        modelName = dtoClass.getName();
        simpleModelName = dtoClass.getSimpleName();
        variableInfoList = getAllVariableInfo(dtoClass, "");
    }

    /**
     * 클래스의 모든 필드 정보를 파싱한다.
     *
     * @param dtoClass : 정보 파싱 대상 DTO 클래스.
     * @param nestedObjectState : 객체 중첩의 정도를 나타내는 정보.
     * @return : VariableInfo 객체의 리스트를 반환한다.
     * @throws Exception
     */
    public List<VariableInfo> getAllVariableInfo(Class<?> dtoClass, String nestedObjectState) throws Exception {

        List<VariableInfo> variableInfoList = new ArrayList<>();

        for(Field field : dtoClass.getDeclaredFields()) {
            field.setAccessible(true);

            // VariableInfo 기본 필드 값 설정.
            String parameter = nestedObjectState + field.getName();
            String type = field.getType().getName();
            String simpleType = field.getType().getSimpleName();
            boolean required = false;
            String description = "";

            // 필드 중 리스트가 있는 경우
            if(simpleType.equals("List")) {
                parameter += "[]";

                // 어떤 타입의 리스트인지 추가적으로 확인한다.
                ParameterizedType pt = (ParameterizedType)field.getGenericType();
                Class<?> cls = (Class<?>)pt.getActualTypeArguments()[0];
                type = cls.getName();
            }

            // 설정한 필드 값에 따라 VariableInfo 객체 생성 후 리스트에 추가.
            variableInfoList.add(new VariableInfo(parameter, type, simpleType, required, description));

            // 추가한 필드가 API DTO Nested Object인 경우 해당 Object의 모든 필드 값을 파싱(재귀 호출 형태로).
            if(!field.getType().isPrimitive()) {
                Class<?> nestedObject = Class.forName(type);
                if(nestedObject.isAnnotationPresent(DTO.class)) {
                    variableInfoList.addAll(getAllVariableInfo(nestedObject, parameter + "."));
                }
            }
        }

        return variableInfoList;
    }
}
