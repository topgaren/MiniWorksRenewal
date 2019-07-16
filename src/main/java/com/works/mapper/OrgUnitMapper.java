package com.works.mapper;

import com.works.entity.OrgUnitEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrgUnitMapper {

    OrgUnitEntity getOrgUnitByExternalKey(@Param("domainId") int domainId, @Param("orgExternalKey") String orgExternalKey);
}
