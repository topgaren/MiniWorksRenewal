package com.works.document;

import com.works.annotation.DTO;
import com.works.annotation.DescriptionAPI;
import com.works.annotation.DescriptionPathParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class APIInfo {

    private String apiNameKorVer;   // API 한글 이름.
    private String apiDescription;  // API 개요.
    private String requestURI;      // API 요청 URI.
    private String httpMethod;      // HTTP Method
    private String apiResponse;     // API 응답에 관한 설명.
    private int apiCode;            // API 고유 번호.

    private List<FieldInfo> pathParameterInfoList;  // Path parameter에 전달되는 field 정보.

    private DTOInfo requestBodyInfo;    // Request Body에 전달되어야 하는 JSON 객체의 정보.
    private DTOInfo responseBodyInfo;   // Response Body에 전달되는 JSON 객체의 정보.

    public APIInfo(Method api) throws Exception {

        // API 기본 정보(한글 이름, 설명, 요청 URI, 응답 메시지, HTTP Method 등)
        apiNameKorVer = api.getAnnotation(DescriptionAPI.class).apiNameKorVer();
        apiDescription = api.getAnnotation(DescriptionAPI.class).description();
        requestURI = api.getAnnotation(RequestMapping.class).value()[0];
        httpMethod = api.getAnnotation(RequestMapping.class).method()[0].toString();
        apiResponse = api.getAnnotation(DescriptionAPI.class).response();
        apiCode = api.getAnnotation(DescriptionAPI.class).apiCode();

        requestBodyInfo = null;
        responseBodyInfo = null;

        // API 메소드 파라미터 정보 파싱
        pathParameterInfoList = new ArrayList<>();
        for(Parameter apiParameter : api.getParameters()) {

            // @PathVariable 어노테이션을 사용하는 파라미터 --> URI를 통해 전달된 파라미터
            if(apiParameter.isAnnotationPresent(PathVariable.class)) {
                FieldInfo parameterInfo = new FieldInfo();
                parameterInfo.setParameter(apiParameter.getAnnotation(PathVariable.class).name());
                parameterInfo.setType(apiParameter.getType().getName());
                parameterInfo.setSimpleType(apiParameter.getType().getSimpleName());
                parameterInfo.setDescription(apiParameter.getAnnotation(DescriptionPathParam.class).value());
                pathParameterInfoList.add(parameterInfo);
            }

            // @RequestBody 어노테이션을 사용하는 파라미터 --> JSON 형태로 전달한 DTO
            if(apiParameter.isAnnotationPresent(RequestBody.class)) {
                requestBodyInfo = new DTOInfo(apiParameter.getType());
            }
        }

        // Returned DTO에 대한 정보 파싱
        Class<?> returnObjectType = api.getReturnType();
        if(returnObjectType.isAnnotationPresent(DTO.class)) {
            responseBodyInfo = new DTOInfo(returnObjectType);
        }
    }
}
