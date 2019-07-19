package com.works.service;

import com.works.dto.UserResponseDTO;
import com.works.entity.UserEntity;
import com.works.exception.BadRequestException;
import com.works.exception.ConflictException;
import com.works.exception.NullException;
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

    /**
     * 구성원 정보를 조회한다.
     *
     * @param domainId : 구성원이 속한 도메인 아이디.
     * @param userExternalKey : 조회하려는 구성원의 외부키.
     * @return 조회한 구성원의 객체(DTO)
     * @throws Exception
     */
    public UserResponseDTO getUserDTO(int domainId, String userExternalKey) throws Exception {

        // 존재하지 않는 도메인을 사용하려는 경우 : BadRequestException 예외 발생.
        if(domainMapper.getDomainCount(domainId) == 0) {
            throw new BadRequestException("domainId에 해당하는 도메인이 존재하지 않습니다.");
        }

        // 도메인 내 존재하지 않는 외부키를 사용하려는 경우 : NullException 예외 발생.
        UserEntity resultUserEntity = userMapper.getUserByExternalKey(domainId, userExternalKey);
        if(resultUserEntity == null) {
            throw new NullException("외부키에 해당하는 구성원이 존재하지 않습니다.");
        }

        return resultUserEntity.toUserDTO();
    }

    /**
     * 구성원 정보를 추가한다.
     *
     * @param userRequestEntity : 추가할 구성원 정보를 담고 있는 객체.
     * @throws Exception
     */
    public void insertUserDTO(UserEntity userRequestEntity) throws Exception {

        // 존재하지 않는 도메인을 사용하려는 경우 : BadRequestException 예외 발생.
        if(domainMapper.getDomainCount(userRequestEntity.getDomainId()) == 0) {
            throw new BadRequestException("domainId에 해당하는 도메인이 존재하지 않습니다.");
        }

        // 도메인 내 중복되는 외부키를 사용하려는 경우 : ConflictException 예외 발생.
        if(userMapper.getUserExist(userRequestEntity.getDomainId(), userRequestEntity.getUserExternalKey()) == 1) {
            throw new ConflictException("도메인 내 이미 사용중인 외부키가 존재합니다.");
        }

        // account의 도메인 파트와 domainId에 해당하는 도메인 주소가 일치하지 않는 경우

        userMapper.insertUser(userRequestEntity);
    }

    /**
     * 구성원 정보를 수정한다.
     * 전달하지 않은 정보는 삭제한다.
     *
     * @param userRequestEntity : 수정할 정보를 담고 있는 객체.
     * @throws Exception
     */
    public void updateAllUserDTO(UserEntity userRequestEntity) throws Exception {

        // 존재하지 않는 도메인을 사용하려는 경우 : BadRequestException 예외 발생.
        if(domainMapper.getDomainCount(userRequestEntity.getDomainId()) == 0) {
            throw new BadRequestException("domainId에 해당하는 도메인이 존재하지 않습니다.");
        }

        // 존재하지 않는 구성원을 수정하려는 경우 : NullException 예외 발생.
        if(userMapper.getUserExist(userRequestEntity.getDomainId(), userRequestEntity.getUserExternalKey()) == 0) {
            throw new NullException("외부키에 해당하는 수정 대상이 존재하지 않습니다.");
        }

        userMapper.updateUser(userRequestEntity);
    }

    /**
     * 구성원 정보를 수정한다.
     * 전달하지 않은 정보는 수정하지 않는다.
     *
     * @param userRequestEntity : 수정할 정보를 담고 있는 객체.
     * @throws Exception
     */
    public void updatePartUserDTO(UserEntity userRequestEntity) throws Exception {

        // 원본 객체를 불러오기 위해 도메인 아이디와 도메인 외부키를 얻는다.
        int domainId = userRequestEntity.getDomainId();
        String userExternalKey = userRequestEntity.getUserExternalKey();

        // 존재하지 않는 도메인을 사용하려는 경우 : BadRequestException 예외 발생.
        if(domainMapper.getDomainCount(domainId) == 0) {
            throw new BadRequestException("domainId에 해당하는 도메인이 존재하지 않습니다.");
        }

        // 존재하지 않는 구성원을 수정하려는 경우 : NullException 예외 발생.
        if(userMapper.getUserExist(domainId, userExternalKey) == 0) {
            throw new NullException("외부키에 해당하는 수정 대상이 존재하지 않습니다.");
        }

        // 원본 객체를 불러온다.
        UserEntity modifiedUserEntity = userMapper.getUserByExternalKey(domainId, userExternalKey);

        // 원본 객체를 수정할 정보를 담고 있는 객체의 정보로 업데이트 한다.
        modifiedUserEntity.updateUser(userRequestEntity);

        // 수정한 객체를 DB에 전달한다.
        userMapper.updateUser(modifiedUserEntity);
    }

    /**
     * 구성원 정보를 삭제한다.
     *
     * @param domainId : 구성원이 속한 도메인 아이디.
     * @param userExternalKey : 구성원 외부키.
     * @throws Exception
     */
    public void deleteUser(int domainId, String userExternalKey) throws Exception {

        // 존재하지 않는 도메인을 사용하려는 경우 : BadRequestException 예외 발생.
        if(domainMapper.getDomainCount(domainId) == 0) {
            throw new BadRequestException("domainId에 해당하는 도메인이 존재하지 않습니다.");
        }

        // 존재하지 않는 구성원을 삭제하려는 경우 : NullException 예외 발생.
        if(userMapper.getUserExist(domainId, userExternalKey) == 0) {
            throw new NullException("외부키에 해당하는 삭제 대상이 존재하지 않습니다.");
        }

        userMapper.deleteUser(domainId, userExternalKey);
    }
}
