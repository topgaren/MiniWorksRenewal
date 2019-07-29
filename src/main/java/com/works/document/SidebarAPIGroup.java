package com.works.document;

import com.works.annotation.DescriptionAPIGroup;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SidebarAPIGroup implements Comparable<SidebarAPIGroup> {

    private String apiGroupName;
    private int apiGroupCode;
    private List<APIInfo> apiInfoList;


    public SidebarAPIGroup(String controllerName) throws Exception {
        Class<?> controllerClass = Class.forName("com.works.controller." + controllerName);
        this.apiGroupName = controllerClass.getAnnotation(DescriptionAPIGroup.class).apiGroupName();
        this.apiGroupCode = controllerClass.getAnnotation(DescriptionAPIGroup.class).apiGroupCode();

        apiInfoList = new ArrayList<>();
        for(Method apiMethod : controllerClass.getMethods()) {
            if(apiMethod.isAnnotationPresent(RequestMapping.class)) {
                apiInfoList.add(new APIInfo(apiMethod));
            }
        }
    }

    @Override
    public int compareTo(SidebarAPIGroup sidebarAPIGroup) {
        return this.apiGroupCode - sidebarAPIGroup.getApiGroupCode();
    }
}
