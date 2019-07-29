package com.works.document;

import com.works.annotation.DescriptionAPI;
import com.works.annotation.DescriptionAPIGroup;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SidebarAPIGroupInfo implements Comparable<SidebarAPIGroupInfo> {

    private String groupName;
    private int groupCode;

    private List<SidebarAPIInfo> sidebarAPIInfoList;

    public SidebarAPIGroupInfo(Class<?> apiController) throws Exception {

        groupName = apiController.getAnnotation(DescriptionAPIGroup.class).apiGroupName();
        groupCode = apiController.getAnnotation(DescriptionAPIGroup.class).apiGroupCode();
        sidebarAPIInfoList = new ArrayList<>();

        for(Method api : apiController.getMethods()) {
            if(api.isAnnotationPresent(DescriptionAPI.class)) {
                APIInfo apiInfo = new APIInfo(api);
                sidebarAPIInfoList.add(new SidebarAPIInfo(apiInfo.getApiNameKorVer(), apiInfo.getApiCode()));
            }
        }
    }

    @Override
    public int compareTo(SidebarAPIGroupInfo comparedSidebarAPIGroupInfo) {
        return this.groupCode - comparedSidebarAPIGroupInfo.getGroupCode();
    }
}
