package com.works.service;

import com.works.dto.OrgUnitResponseDTO;
import com.works.entity.OrgUnitEntity;
import com.works.mapper.OrgUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgUnitService {

    @Autowired
    OrgUnitMapper orgUnitMapper;

    public OrgUnitResponseDTO getOrgUnitDTO(int domainId, String orgExternalKey) {

        OrgUnitEntity resultOrgUnitEntity = orgUnitMapper.getOrgUnitByExternalKey(domainId, orgExternalKey);
        return resultOrgUnitEntity.toOrgUnitDTO();
    }

}
