package com.works.document;

import com.works.annotation.DTO;
import com.works.annotation.DescriptionAPI;
import com.works.annotation.DescriptionAPIGroup;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class DocumentInfo {

    private List<DTOInfo> allDTOInfoList;
    private List<APIInfo> allAPIInfoList;
    private List<SidebarAPIGroupInfo> sidebarAPIGroupInfoList;

    public DocumentInfo() throws Exception {

        allDTOInfoList = new ArrayList<>();
        allAPIInfoList = new ArrayList<>();
        sidebarAPIGroupInfoList = new ArrayList<>();

        Reflections reflections = new Reflections("com.works");
        Set<Class<?>> dtoClassSet = reflections.getTypesAnnotatedWith(DTO.class);
        Set<Class<?>> apiGroupSet = reflections.getTypesAnnotatedWith(DescriptionAPIGroup.class);

        for(Class<?> dtoClass : dtoClassSet) {
            allDTOInfoList.add(new DTOInfo(dtoClass));
        }

        for(Class<?> apiGroup : apiGroupSet) {
            sidebarAPIGroupInfoList.add(new SidebarAPIGroupInfo(apiGroup));

            for(Method api : apiGroup.getMethods()) {
                if(api.isAnnotationPresent(DescriptionAPI.class)) {
                    allAPIInfoList.add(new APIInfo(api));
                }
            }
        }
        Collections.sort(sidebarAPIGroupInfoList);
    }

    public APIInfo getApiInfoByApiCode(int apiCode) {

        if(this.allAPIInfoList.isEmpty()) {
            return null;
        }

        APIInfo resultAPIInfo = null;

        for(APIInfo apiInfo : this.allAPIInfoList) {
            if(apiInfo.getApiCode() == apiCode) {
                resultAPIInfo = apiInfo;
                break;
            }
        }

        return resultAPIInfo;
    }

}
