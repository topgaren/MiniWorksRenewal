package com.works.service;

import com.works.dto.OrgUnitRequestCreateDTO;
import com.works.dto.OrgUnitResponseDTO;
import com.works.entity.OrgUnitEntity;
import com.works.mapper.OrgUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OrgUnitService {

    @Autowired
    OrgUnitMapper orgUnitMapper;

    public OrgUnitResponseDTO getOrgUnitDTO(int domainId, String orgExternalKey) {

        OrgUnitEntity resultOrgUnitEntity = orgUnitMapper.getOrgUnitByExternalKey(domainId, orgExternalKey);
        return resultOrgUnitEntity.toOrgUnitDTO();
    }

    public void insertOrgUnit(String parnetOrgUnitExternalKey, OrgUnitEntity orgUnitRequestEntity) {

        int domainId = orgUnitRequestEntity.getDomainId();

        // 부모 조직을 특별히 지정하지 않은 경우 depth는 1로, parentId는 0으로 설정된다.
        // (참조 : OrgUnitRequestCreateDTO.toOrgUnitEntity())

        // 특정 조직을 부모 조직으로 지정한 경우 depth와 parentId를 따로 설정한다.
        if(!StringUtils.isEmpty(parnetOrgUnitExternalKey)) {

            // 부모 조직을 얻어온다.
            OrgUnitEntity parentOrgUnitEntity = orgUnitMapper.getOrgUnitByExternalKey(domainId, parnetOrgUnitExternalKey);

            // depth 설정(= 부모 조직의 depth + 1)
            // Depth 제한이 있는 경우 예외처리 코드 추가할 것.
            orgUnitRequestEntity.setDepth(parentOrgUnitEntity.getDepth() + 1);

            // 부모 조직 아이디 설정.
            orgUnitRequestEntity.setParentId(parentOrgUnitEntity.getOrgId());
        }

        // sibling order 설정(= 현재 부모 조직의 자손 조직 조직 수 + 1)
        // 즉 부모 조직의 마지막 형제로 들어간다.
        int numOfSibling = orgUnitMapper.countChildById(domainId, orgUnitRequestEntity.getParentId());
        orgUnitRequestEntity.setSiblingOrder(numOfSibling + 1);

        orgUnitMapper.insertOrgUnit(orgUnitRequestEntity);
    }

    public void deleteOrgUnit(int domainId, String orgExternalKey) {

        orgUnitMapper.deleteOrgUnit(domainId, orgExternalKey);
    }
}
