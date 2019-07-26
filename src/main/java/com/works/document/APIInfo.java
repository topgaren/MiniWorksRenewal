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

    private String apiNameKorVer;
    private String apiDescription;
    private String requestURI;
    private String httpMethod;
    private String apiResponse;
    private int apiCode;

    private List<FieldInfo> pathParameterInfoList;

    private DTOInfo requestBodyInfo;
    private DTOInfo responseBodyInfo;

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
                String parameter = apiParameter.getAnnotation(PathVariable.class).name();
                String type = apiParameter.getType().getName();
                String simpleType = apiParameter.getType().getSimpleName();
                String description = apiParameter.getAnnotation(DescriptionPathParam.class).value();
                pathParameterInfoList.add(new FieldInfo(parameter, type, simpleType, true, description));
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
