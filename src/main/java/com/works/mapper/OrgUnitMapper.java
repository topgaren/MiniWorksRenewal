package com.works.mapper;

import com.works.entity.OrgUnitEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrgUnitMapper {

    /**
     * 조직 정보를 조회한다.
     *
     * @param   domainId 조직이 속한 도메인 아이디.
     * @param   orgExternalKey 조직의 외부키.
     * @return  조회한 조직 정보를 담고 있는 객체
     */
    OrgUnitEntity getOrgUnitByExternalKey(@Param("domainId") int domainId, @Param("orgExternalKey") String orgExternalKey);

    /**
     * 조직 정보를 새로 추가한다.
     *
     * @param   orgUnitEntity 새로 추가할 조직 정보를 담고 있는 객체.
     */
    void insertOrgUnit(OrgUnitEntity orgUnitEntity);

    /**
     * 조직 정보를 삭제한다.
     *
     * @param   domainId 조직이 속한 도메인의 아이디.
     * @param   orgExternalKey 조직의 외부키.
     */
    void deleteOrgUnit(@Param("domainId") int domainId, @Param("orgExternalKey") String orgExternalKey);
}
