package com.works;

import com.works.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/")
    public String hello() throws Exception {
        Integer userCount = userMapper.getUserCount(100);

        return userCount.toString();
    }
}
