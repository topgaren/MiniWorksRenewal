package com.works;

import com.works.exception.AppError;
import com.works.exception.NullException;
import com.works.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/")
    public String hello() {
        Integer userCount = userMapper.getUserCount(100);

        return userCount.toString();
    }

    @GetMapping("/exception")
    public AppError testException() throws Exception {
        throw new NullException("This is NullException Test!");
    }
}
