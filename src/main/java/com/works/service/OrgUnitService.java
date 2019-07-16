package com.works.service;

import com.works.dto.OrgUnitRequestCreateDTO;
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

    public void insertOrgUnitDTO(OrgUnitRequestCreateDTO orgUnitRequestDTO) {

        OrgUnitEntity orgUnitEntity = orgUnitRequestDTO.toOrgUnitEntity();
        orgUnitMapper.insertOrgUnit(orgUnitEntity);
    }

    public void deleteOrgUnit(int domainId, String orgExternalKey) {

        orgUnitMapper.deleteOrgUnit(domainId, orgExternalKey);
    }
}
