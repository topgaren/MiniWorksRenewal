package com.works.service;

import com.works.dto.UserRequestDTO;
import com.works.dto.UserResponseDTO;
import com.works.entity.User;
import com.works.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public UserResponseDTO getUserDTO(int domainId, String userExternalKey) {

        User resultUser = userMapper.getUserByExternalKey(domainId, userExternalKey);
        return resultUser.toUserDTO();
    }

    public void insertUserDTO(UserRequestDTO userRequestDTO) {

        userMapper.insertUser(userRequestDTO.toUserEntity());
    }

    public void updateAllUserDTO(UserRequestDTO userRequestDTO) {

    }

    public void updatePartUserDTO(UserRequestDTO userRequestDTO) {

        User requestUserEntity = userRequestDTO.toUserEntity();

        int domainId = requestUserEntity.getDomainId();
        String userExternalKey = requestUserEntity.getUserExternalKey();

        User modifiedUserEntity = userMapper.getUserByExternalKey(domainId, userExternalKey);
    }

    public void deleteUser(int domainId, String userExternalKey) {

        userMapper.deleteUser(domainId, userExternalKey);
    }
}
