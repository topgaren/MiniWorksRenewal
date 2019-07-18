package com.works.mapper;

import java.util.List;
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
     * 조직 정보를 수정한다.
     *
     * @param   orgUnitEntity 수정된 조직 정보를 담고 있는 객체
     */
    void updateOrgUnit(OrgUnitEntity orgUnitEntity);

    /**
     * 조직 정보를 삭제한다.
     *
     * @param   domainId 조직이 속한 도메인의 아이디.
     * @param   orgExternalKey 조직의 외부키.
     */
    void deleteOrgUnit(@Param("domainId") int domainId, @Param("orgExternalKey") String orgExternalKey);

    /**
     * 자손 조직의 개수를 센다.
     *
     * @param   domainId 조직이 속한 도메인의 아이디.
     * @param   parentId 부모 조직의 아이디.
     * @return  아이디가 parentId인 조직을 부모로 갖는 조직의 개수를 반환한다.
     */
    int countChildById(@Param("domainId") int domainId, @Param("parentId") int parentId);


    /**
     * 직계 자손들의 객체 정보를 리스트 형태로 얻는다. 리스트 내의 직계 자손의 순서는 Sibling Order를 따른다.
     *
     * @param   domainId 조직이 속한 도메인의 아이디.
     * @param   parentId 부모 조직 아이디.
     * @return  parentId를 갖는 조직의 직계 자손 조직 정보를 리스트 형태로 반환한다.
     */
    List<OrgUnitEntity> getChildrenById(@Param("domainId") int domainId, @Param("parentId") int parentId);
}
