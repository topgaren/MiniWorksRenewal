package com.works.service;

import com.works.dto.UserRequestCreateDTO;
import com.works.dto.UserRequestUpdateDTO;
import com.works.dto.UserResponseDTO;
import com.works.entity.UserEntity;
import com.works.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public UserResponseDTO getUserDTO(int domainId, String userExternalKey) {

        UserEntity resultUserEntity = userMapper.getUserByExternalKey(domainId, userExternalKey);
        return resultUserEntity.toUserDTO();
    }

    public void insertUserDTO(UserRequestCreateDTO userRequestDTO) {

        UserEntity userRequestEntity = userRequestDTO.toUserEntity();
        userMapper.insertUser(userRequestEntity);
    }

    public void updateAllUserDTO(UserRequestUpdateDTO userRequestDTO) {

        // 일단 domainId와 externalKey는 업데이트 하지 않는다는 가정을 한다.
        userMapper.updateUser(userRequestDTO.toUserEntity());
    }

    public void updatePartUserDTO(UserRequestUpdateDTO userRequestDTO) {

        // 수정할 정보를 담고 있는 User 객체
        UserEntity requestUserEntity = userRequestDTO.toUserEntity();

        int domainId = requestUserEntity.getDomainId();
        String userExternalKey = requestUserEntity.getUserExternalKey();

        // 원본 객체를 불러온다.
        UserEntity modifiedUserEntity = userMapper.getUserByExternalKey(domainId, userExternalKey);

        // 원본 객체를 수정할 정보를 담고 있는 객체의 정보로 업데이트 한다.
        modifiedUserEntity.updateUser(requestUserEntity);

        // 수정한 객체를 DB에 전달한다.
        userMapper.updateUser(modifiedUserEntity);
    }

    public void deleteUser(int domainId, String userExternalKey) {

        userMapper.deleteUser(domainId, userExternalKey);
    }
}
