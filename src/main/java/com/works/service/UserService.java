package com.works.service;

import com.works.dto.UserResponseDTO;
import com.works.entity.UserEntity;
import com.works.exception.BadRequestException;
import com.works.exception.ConflictException;
import com.works.mapper.DomainMapper;
import com.works.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    DomainMapper domainMapper;

    @Autowired
    UserMapper userMapper;

    public UserResponseDTO getUserDTO(int domainId, String userExternalKey) throws Exception {

        UserEntity resultUserEntity = userMapper.getUserByExternalKey(domainId, userExternalKey);
        return resultUserEntity.toUserDTO();
    }

    public void insertUserDTO(UserEntity userRequestEntity) throws Exception {
        // 존재하지 않는 도메인을 사용하려는 경우 : BadRequestException 예외 발생
        if(domainMapper.getDomainCount(userRequestEntity.getDomainId()) == 0) {
            throw new BadRequestException("domainId에 해당하는 도메인이 존재하지 않습니다.");
        }

        // 도메인 내 중복되는 외부키를 사용하려는 경우 : ConflictException 예외 발생
        if(userMapper.getUserExist(userRequestEntity.getDomainId(), userRequestEntity.getUserExternalKey()) == 1) {
            throw new ConflictException("도메인 내 이미 사용중인 외부키가 존재합니다.");
        }

        userMapper.insertUser(userRequestEntity);
    }

    public void updateAllUserDTO(UserEntity userRequestEntity) {

        // 일단 domainId와 externalKey는 업데이트 하지 않는다는 가정을 한다.
        // 따라서 UserRequestUpdateDTO에 userExternalKey 필드를 제외하였다.
        userMapper.updateUser(userRequestEntity);
    }

    public void updatePartUserDTO(UserEntity userRequestEntity) {

        // 원본 객체를 불러오기 위해 도메인 아이디와 도메인 외부키를 얻는다.
        int domainId = userRequestEntity.getDomainId();
        String userExternalKey = userRequestEntity.getUserExternalKey();

        // 원본 객체를 불러온다.
        UserEntity modifiedUserEntity = userMapper.getUserByExternalKey(domainId, userExternalKey);

        // 원본 객체를 수정할 정보를 담고 있는 객체의 정보로 업데이트 한다.
        modifiedUserEntity.updateUser(userRequestEntity);

        // 수정한 객체를 DB에 전달한다.
        userMapper.updateUser(modifiedUserEntity);
    }

    public void deleteUser(int domainId, String userExternalKey) {

        userMapper.deleteUser(domainId, userExternalKey);
    }
}
