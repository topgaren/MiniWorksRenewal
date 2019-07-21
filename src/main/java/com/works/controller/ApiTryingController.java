package com.works.controller;

import com.works.document.DTOInfo;
import com.works.document.VariableInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.ArrayList;

@Controller
public class ApiTryingController {

    @RequestMapping("/documents/main")
    public String documentsMain(Model model) {

        List<String> methodList = new ArrayList<>();
        methodList.add("Get-Version3");
        methodList.add("Post-Version3");
        methodList.add("Put-Version3");
        methodList.add("Patch-Version3");
        methodList.add("Delete-Version3");
        methodList.add("Patch-Version4");
        methodList.add("Get-Version4");
        methodList.add("Put-Version2");
        model.addAttribute("methodList", methodList);

        String thymeleaf = "Hello Thymeleaf!";
        model.addAttribute("test", thymeleaf);

        return "main";
    }

    @RequestMapping("/documents/usertest")
    @ResponseBody
    public List<VariableInfo> usertest() throws Exception {
        Class<?> userDTOClass = Class.forName("com.works.dto.UserRequestCreateDTO");
        DTOInfo userDTOInfo = new DTOInfo(userDTOClass);

        return userDTOInfo.getVariableInfoList();
    }
}
