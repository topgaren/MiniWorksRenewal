package com.works.controller;


import com.works.annotation.DescriptionAPI;
import com.works.document.APIInfo;
import com.works.document.DTOInfo;
import com.works.document.SidebarAPIGroup;
import com.works.dto.UserRequestCreateDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;

@Controller
public class ApiTryingController {

    private List<SidebarAPIGroup> sidebarAPIGroupList;

    public ApiTryingController() throws Exception {
        sidebarAPIGroupList = new ArrayList<>();
    }

    @RequestMapping("/documents/main")
    public String documentsMain(Model model) throws Exception {

        // 최초 한번만 실행되는 코드
        if(sidebarAPIGroupList.isEmpty()) {
            sidebarAPIGroupList.add(new SidebarAPIGroup("UserController"));
            sidebarAPIGroupList.add(new SidebarAPIGroup("OrgUnitController"));
        }

        // 구성원 API Group
        model.addAttribute("userAPIGroup", sidebarAPIGroupList.get(0));

        // 조직 API Group
        model.addAttribute("orgAPIGroup", sidebarAPIGroupList.get(1));

        String thymeleaf = "Hello Thymeleaf!";
        model.addAttribute("test", thymeleaf);

        return "DocumentsMain";
    }




    @RequestMapping("/documents/{apiCode}")
    public String documentsApi(@PathVariable int apiCode, Model model) throws Exception {

        // 최초 한번만 실행되는 코드
        if(sidebarAPIGroupList.isEmpty()) {
            sidebarAPIGroupList.add(new SidebarAPIGroup("UserController"));
            sidebarAPIGroupList.add(new SidebarAPIGroup("OrgUnitController"));
        }

        // 구성원 API Group
        model.addAttribute("userAPIGroup", sidebarAPIGroupList.get(0));

        // 조직 API Group
        model.addAttribute("orgAPIGroup", sidebarAPIGroupList.get(1));

        // apiCode로 문서화할 APIInfo 객체 얻어오기
        APIInfo targetAPIInfo = null;
        for(SidebarAPIGroup sidebarAPIGroup : sidebarAPIGroupList) {
            for(APIInfo apiInfo : sidebarAPIGroup.getApiInfoList()) {
                if(apiCode == apiInfo.getApiCode()) {
                    targetAPIInfo = apiInfo;
                    break;
                }
            }
        }

        // 존재하지 않는 apiCode를 사용하여 targetApiInfo를 얻어오지 못했다면 최초 화면으로 이동.
        if(targetAPIInfo == null) {
            return "DocumentsMain";
        }

        model.addAttribute("apiInfo", targetAPIInfo);

        return "DocumentsAPI";
    }

    @RequestMapping("/documents/getdto")
    @ResponseBody
    public DTOInfo getDTO() throws Exception {
        Class<?> cls = Class.forName("com.works.dto.UserRequestCreateDTO");
        DTOInfo result = new DTOInfo(cls);

        return result;
    }
}
