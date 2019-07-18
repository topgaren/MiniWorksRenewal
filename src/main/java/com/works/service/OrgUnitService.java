package com.works.service;

import java.util.ArrayList;
import java.util.List;

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

    public List<OrgUnitEntity> getDescendantById(int domainId, int parentId) {

        // 후손 조직 객체를 저장할 리스트
        List<OrgUnitEntity> descendantOrgUnitEntities = new ArrayList<>();

        // 직계 자손 조직을 얻어온다.
        List<OrgUnitEntity> childrenOrgUnitEntities = orgUnitMapper.getChildrenById(domainId, parentId);
        descendantOrgUnitEntities.addAll(childrenOrgUnitEntities);

        // 각 자손 조직의 후손 조직을 재귀 형태로 얻어온다.
        for(OrgUnitEntity childOrgUnitEntity : childrenOrgUnitEntities) {
            descendantOrgUnitEntities.addAll(getDescendantById(domainId, childOrgUnitEntity.getOrgId()));
        }

        return descendantOrgUnitEntities;
    }

    public void updateAllOrgUnit(OrgUnitEntity requestOrgUnitEntity) {

        int domainId = requestOrgUnitEntity.getDomainId();
        String orgExternalKey = requestOrgUnitEntity.getOrgExternalKey();

        // 원본 OrgUnit 객체를 얻어온다.
        OrgUnitEntity originalOrgUnitEntity = orgUnitMapper.getOrgUnitByExternalKey(domainId, orgExternalKey);

        // 수정할 수 없는 핵심 정보들(dpeth, siblingOrder, parentId 등)은 원본 객체의 것을 그대로 사용한다.
        requestOrgUnitEntity.setDepth(originalOrgUnitEntity.getDepth());
        requestOrgUnitEntity.setSiblingOrder(originalOrgUnitEntity.getSiblingOrder());
        requestOrgUnitEntity.setParentId(originalOrgUnitEntity.getParentId());

        // 업데이트 수행.
        orgUnitMapper.updateOrgUnit(requestOrgUnitEntity);
    }

    public void updatePartOrgUnit(OrgUnitEntity requestOrgUnitEntity) {

        int domainId = requestOrgUnitEntity.getDomainId();
        String orgExternalKey = requestOrgUnitEntity.getOrgExternalKey();

        // 원본 OrgUnit 객체를 얻어온다.
        OrgUnitEntity originalOrgUnitEntity = orgUnitMapper.getOrgUnitByExternalKey(domainId, orgExternalKey);

        // 객체 수정 후 업데이트 수행
        OrgUnitEntity updatedOrgUnitEntity = originalOrgUnitEntity;
        updatedOrgUnitEntity.updateOrgUnit(requestOrgUnitEntity);
        orgUnitMapper.updateOrgUnit(updatedOrgUnitEntity);
    }

    public void moveOrgUnit(int domainId, String orgExternalKey, String parentOrgExternalKey, String prevOrgExternalKey) {

        // 아이디와 외부키에 대한 예외 처리 코드 추가할 것.


        // 이동 대상, 이동 후 부모, 이동 후 바로 앞 형제 조직 객체를 모두 얻어온다.
        OrgUnitEntity movingOrgUnitEntity = orgUnitMapper.getOrgUnitByExternalKey(domainId, orgExternalKey);
        OrgUnitEntity newParentOrgUnitEntity = StringUtils.isEmpty(parentOrgExternalKey) ? new OrgUnitEntity()
                : orgUnitMapper.getOrgUnitByExternalKey(domainId, parentOrgExternalKey);
        OrgUnitEntity newPrevOrgUnitEntity = StringUtils.isEmpty(prevOrgExternalKey) ? new OrgUnitEntity()
                : orgUnitMapper.getOrgUnitByExternalKey(domainId, prevOrgExternalKey);

        // 이동 전/후 부모 조직 아이디 및 현재 order, 새로운 직전 형제의 order 등을 얻는다.
        int currentParentId = movingOrgUnitEntity.getParentId();
        int newParentId = newParentOrgUnitEntity.getOrgId();
        int currentSiblingOrder = movingOrgUnitEntity.getSiblingOrder();
        int newPrevSiblingOrder = newPrevOrgUnitEntity.getSiblingOrder();

        // 제자리 움직임 체크.
        if(currentParentId == newParentId && currentSiblingOrder - 1 == newPrevSiblingOrder) {
            // 제자리 움직임 조건 1 : 현재 부모 조직 아이디와 이동 후 부모 조직 아이디가 일치한다.
            // 제자리 움직임 조건 2 : 현재 바로 직전 형제 조직의 sibling order 값과 이동하고자 하는 직전 형제 조직의 sibling order 값이 일치한다.
            // 조건 1과 조건 2를 모두 만족하는 경우 ==> 제자리 움직임.
            return;
        }

        // 불가능 움직임 체크.

        // Step 1 : depth 업데이트 (이동 대상 및 후손 조직은 모두 동일한 값의 depth 변화량을 갖는다. 이를 depthDiff로 정의)
        int currentDepth = movingOrgUnitEntity.getDepth();      // currentDepth : 이동 전 현재의 depth
        int newParentDepth = newParentOrgUnitEntity.getDepth(); // newParentDepth : 이동 후 새로운 부모 조직의 depth
        int depthDiff = (newParentDepth + 1) - currentDepth;    // 이동 후 depth : newParentDepth + 1 = currentDepth + depthDiff
        if(depthDiff != 0) {
            // depth에 변화가 생긴 경우에만, 즉 depthDiff가 0이 아닌 경우에만 수행

            // 이동 대상 조직의 depth 업데이트
            movingOrgUnitEntity.setDepth(movingOrgUnitEntity.getDepth() + depthDiff);
            orgUnitMapper.updateOrgUnit(movingOrgUnitEntity);

            // 이동 대상 조직의 모든 후손 조직의 depth 업데이트
            List<OrgUnitEntity> descendantOrgUnitEntities = getDescendantById(domainId, movingOrgUnitEntity.getOrgId());
            for(OrgUnitEntity childOrgUnitEntity : descendantOrgUnitEntities) {
                childOrgUnitEntity.setDepth(childOrgUnitEntity.getDepth() + depthDiff);
                orgUnitMapper.updateOrgUnit(childOrgUnitEntity);
            }
        }

        // Step 2 : sibling order 업데이트
        if(currentParentId == newParentId) {
            // Case 1 : 기존의 부모 조직 내에서 이동하는 경우

            // 우선 형제 조직 객체들을 얻어온다.
            List<OrgUnitEntity> siblingOrgUnitEntities = orgUnitMapper.getChildrenById(domainId, currentParentId);

            if(newPrevSiblingOrder < currentSiblingOrder) {
                // Case 1-1 : 상위로(sibling order 값이 작아지는 방향으로) 이동하는 경우

                // 조건에 맞는 형제 조직의 sibling order 값을 업데이트 한다.
                for(OrgUnitEntity siblingOrgUnitEntity : siblingOrgUnitEntities) {
                    int siblingOrder = siblingOrgUnitEntity.getSiblingOrder();
                    if(newPrevSiblingOrder < siblingOrder && siblingOrder < currentSiblingOrder) {
                        siblingOrgUnitEntity.setSiblingOrder(siblingOrder + 1);
                        orgUnitMapper.updateOrgUnit(siblingOrgUnitEntity);
                    }
                }

                // 이동 대상에 새로운 sibling order 값 설정
                movingOrgUnitEntity.setSiblingOrder(newPrevSiblingOrder + 1);
                orgUnitMapper.updateOrgUnit(movingOrgUnitEntity);

            } else if(newPrevSiblingOrder > movingOrgUnitEntity.getSiblingOrder()) {
                // Case 1-2 : 하위로(sibling order 값이 커지는 방향으로) 이동하는 경우

                // 조건에 맞는 형제 조직의 sibling order 값을 업데이트 한다.
                for(OrgUnitEntity siblingOrgUnitEntity : siblingOrgUnitEntities) {
                    int siblingOrder = siblingOrgUnitEntity.getSiblingOrder();
                    if(currentSiblingOrder < siblingOrder && siblingOrder <= newPrevSiblingOrder) {
                        siblingOrgUnitEntity.setSiblingOrder(siblingOrder - 1);
                        orgUnitMapper.updateOrgUnit(siblingOrgUnitEntity);
                    }
                }

                // 이동 대상에 새로운 sibling order 값 설정
                movingOrgUnitEntity.setSiblingOrder(newPrevSiblingOrder);
                orgUnitMapper.updateOrgUnit(movingOrgUnitEntity);
            }
        } else {
            // Case 2 : 새로운 부모 조직 아래로 이동하는 경우

            // 기존 형제 조직들과 새로운 형제 조직들의 객체 정보를 얻어온다.
            List<OrgUnitEntity> currentSiblingOrgUnitEntities = orgUnitMapper.getChildrenById(domainId, currentParentId);
            List<OrgUnitEntity> newSiblingOrgUnitEntities = orgUnitMapper.getChildrenById(domainId, newParentId);

            // 기존 형제 조직들의 경우 : 이동 대상 보다 order 값이 큰 조직들은 order 값이 1씩 감소
            for(OrgUnitEntity siblingOrgUnitEntity : currentSiblingOrgUnitEntities) {
                int siblingOrder = siblingOrgUnitEntity.getSiblingOrder();
                if(siblingOrder > currentSiblingOrder) {
                    siblingOrgUnitEntity.setSiblingOrder(siblingOrder - 1);
                    orgUnitMapper.updateOrgUnit(siblingOrgUnitEntity);
                }
            }

            // 새로운 형제 조직들의 경우 : prevOrgExternalKey로 지정한 조직 보다 order 값이 큰 조직들은 order 값을 1씩 증가.
            for(OrgUnitEntity siblingOrgUnitEntity : newSiblingOrgUnitEntities) {
                int siblingOrder = siblingOrgUnitEntity.getSiblingOrder();
                if(siblingOrder > newPrevSiblingOrder) {
                    siblingOrgUnitEntity.setSiblingOrder(siblingOrder + 1);
                    orgUnitMapper.updateOrgUnit(siblingOrgUnitEntity);
                }
            }

            // 이동 대상에 새로운 sibling order 값 설정
            movingOrgUnitEntity.setSiblingOrder(newPrevSiblingOrder + 1);
            orgUnitMapper.updateOrgUnit(movingOrgUnitEntity);
        }

        // Step 3 : parentId 업데이트
        movingOrgUnitEntity.setParentId(newParentId);
        orgUnitMapper.updateOrgUnit(movingOrgUnitEntity);
    }

    public void deleteOrgUnit(int domainId, String orgExternalKey) {

        orgUnitMapper.deleteOrgUnit(domainId, orgExternalKey);
    }
}
