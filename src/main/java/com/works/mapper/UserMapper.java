package com.works.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 도메인에 등록된 구성원(User)의 수를 구한다.
     *
     * @param   domainId 구성원의 수를 조회할 도메인의 아이디.
     * @return  도메인에 등록된 구성원 수.
     */
    int getUserCount(int domainId);
}
