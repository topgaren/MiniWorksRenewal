package com.works.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DomainMapper {

    /**
     * 도메인의 개수를 가져온다.
     * 도메인의 존재 여부를 파악하기 위해 사용한다.
     *
     * @param domainId : 도메인 아이디.
     * @return : 도메인 아이디에 해당하는 도메인의 개수를 반환.
     */
    int getDomainCount(int domainId);
}
