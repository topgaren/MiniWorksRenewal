package com.works.controller;


import com.works.document.APIInfo;
import com.works.document.DTOInfo;
import com.works.document.DocumentInfo;
import com.works.document.SidebarAPIGroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.ArrayList;

@Controller
public class ApiTryingController {

    private DocumentInfo documentInfo;

    public ApiTryingController() throws Exception {

        documentInfo = new DocumentInfo();
    }

    @RequestMapping("/documents/main")
    public String documentsMain(Model model) throws Exception {

        // 사이드 바를 구성하기 위한 정보 전달.
        model.addAttribute("sidebarApiGroupInfoList", documentInfo.getSidebarAPIGroupInfoList());

        String thymeleaf = "Hello Thymeleaf!";
        model.addAttribute("test", thymeleaf);

        return "DocumentsMain";
    }

    @RequestMapping("/documents/{apiCode}")
    public String documentsApi(@PathVariable int apiCode, Model model) throws Exception {

        // 사이드 바를 구성하기 위한 정보 전달.
        model.addAttribute("sidebarApiGroupInfoList", documentInfo.getSidebarAPIGroupInfoList());

        // Documentation 대상 APIInfo 전달.
        model.addAttribute("apiInfo", documentInfo.getApiInfoByApiCode(apiCode));

        // Table 및 Trying Tool에 사용되는 모든 DTOInfo 전달.
        model.addAttribute("allDTOInfoList", documentInfo.getAllDTOInfoList());

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
