package com.works.controller;

import com.works.dto.UserRequestDTO;
import com.works.dto.UserResponseDTO;
import com.works.mapper.UserMapper;
import com.works.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/domains/{domainId}/users/{userExternalKey}")
    public UserResponseDTO getUserDTO(@PathVariable int domainId, @PathVariable String userExternalKey) {

        return userService.getUserDTO(domainId, userExternalKey);
    }

    @PostMapping("/domains/{domainId}/users/{userExternalKey}")
    public void insertUserDTO(@PathVariable int domainId, @PathVariable String userExternalKey,
                              @RequestBody UserRequestDTO userRequestDTO) {

        userRequestDTO.setDomainId(domainId);
        userRequestDTO.setUserExternalKey(userExternalKey);

        userService.insertUserDTO(userRequestDTO);
    }

    @PutMapping("/domains/{domainId}/users/{userExternalKey}")
    public void updateAllUserDTO(@PathVariable int domainId, @PathVariable String userExternalKey,
                                 @RequestBody UserRequestDTO userRequestDTO) {

    }

    @PatchMapping("/domains/{domainId}/users/{userExternalKey}")
    public void updatePartUserDTO(@PathVariable int domainId, @PathVariable String userExternalKey,
                                  @RequestBody UserRequestDTO userRequestDTO) {

    }

    @DeleteMapping("/domains/{domainId}/users/{userExternalKey}")
    public void deleteUser(@PathVariable int domainId, @PathVariable String userExternalKey) {

        userService.deleteUser(domainId, userExternalKey);
    }
}
