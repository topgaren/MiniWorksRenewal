package com.works.mapper;

import com.works.dto.UserResponseDTO;
import com.works.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    /**
     * 도메인에 등록된 구성원(User)의 수를 구한다.
     *
     * @param   domainId 구성원의 수를 조회할 도메인의 아이디.
     * @return  도메인에 등록된 구성원 수.
     */
    int getUserCount(int domainId);

    /**
     * 구성원 외부키에 해당되는 구성원을 조회한다.
     *
     * @param   domainId 조회할 구성원이 속한 도메인의 아이디.
     * @param   userExternalKey 조회할 구성원의 외부키
     * @return  조회한 구성원 정보.
     */
    User getUserByExternalKey(@Param("domainId") int domainId, @Param("userExternalKey") String userExternalKey);

    /**
     * 구성원 정보를 새로 추가한다.
     *
     * @param   user 추가할 구성원 정보를 저장하고 있는 User 객체
     */
    void insertUser(User user);

    /**
     * 구성원 정보를 수정한다.
     *
     * @param  user 수정할 구성원 정보를 저장하고 있는 User 객체
     */
    void updateUser(User user);

    /**
     * 구성원 정보를 삭제한다.
     *
     * @param   domainId 삭제할 구성원이 속한 도메인의 아이디.
     * @param   userExternalKey 삭제할 구성원의 외부키
     */
    void deleteUser(@Param("domainId") int domainId, @Param("userExternalKey") String userExternalKey);
}
