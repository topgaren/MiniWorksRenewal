package com.works.controller;

import com.works.dto.OrgUnitRequestCreateDTO;
import com.works.dto.OrgUnitRequestMoveDTO;
import com.works.dto.OrgUnitRequestUpdateDTO;
import com.works.dto.OrgUnitResponseDTO;
import com.works.entity.OrgUnitEntity;
import com.works.service.OrgUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class OrgUnitController {

    @Autowired
    OrgUnitService orgUnitService;

    /**
     * 조직 정보 단건 조회.
     *
     * @param domainId : 조직이 속한 도메인 아이디.
     * @param orgExternalKey : 조직의 외부키.
     * @return : 조회한 OrgUnitDTO 객체.
     */
    @GetMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public OrgUnitResponseDTO getOrgUnitDTO(@PathVariable int domainId, @PathVariable String orgExternalKey) throws Exception {

        return orgUnitService.getOrgUnitDTO(domainId, orgExternalKey);
    }

    /**
     * 조직 정보 추가.
     *
     * @param domainId : 조직이 속한 도메인 아이디.
     * @param orgExternalKey : 조직의 외부키.
     * @param orgUnitRequestDTO : 추가할 조직 정보를 담고 있는 객체.
     */
    @PostMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public void insertOrgUnit(@PathVariable int domainId, @PathVariable String orgExternalKey,
                              @RequestBody OrgUnitRequestCreateDTO orgUnitRequestDTO) throws Exception {

        // 도메인 아이디와 외부키에 관한 예외 처리 코드 추가할 것.
        OrgUnitEntity orgUnitRequestEntity = orgUnitRequestDTO.toOrgUnitEntity();
        orgUnitRequestEntity.setDomainId(domainId);
        orgUnitRequestEntity.setOrgExternalKey(orgExternalKey);

        // 부모 조직 외부키에 관한 예외 처리는 어디서 할까
        String parentOrgUnitExternalKey = orgUnitRequestDTO.getParentOrgExternalKey();

        orgUnitService.insertOrgUnit(parentOrgUnitExternalKey, orgUnitRequestEntity);
    }

    /**
     * 조직 정보 전체 수정.
     * 전달되지 않은 정보는 지워진다.
     *
     * @param domainId : 조직이 속한 도메인 아이디.
     * @param orgExternalKey : 조직의 외부키.
     * @param orgUnitRequestDTO : 수정 정보를 담고 있는 객체.
     */
    @PutMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public void updateAllOrgUnit(@PathVariable int domainId, @PathVariable String orgExternalKey,
                                 @RequestBody OrgUnitRequestUpdateDTO orgUnitRequestDTO) throws Exception {

        OrgUnitEntity requestOrgUnitEntity = orgUnitRequestDTO.toOrgUnitEntity();
        requestOrgUnitEntity.setDomainId(domainId);
        requestOrgUnitEntity.setOrgExternalKey(orgExternalKey);

        orgUnitService.updateAllOrgUnit(requestOrgUnitEntity);
    }

    /**
     * 조직 정보 부분 수정.
     * 전달 되지 않은 정보는 수정하지 않는다.
     *
     * @param domainId : 조직이 속한 도메인 아이디.
     * @param orgExternalKey : 조직의 외부키.
     * @param orgUnitRequestDTO : 수정 정보를 담고 있는 객체.
     */
    @PatchMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public void updatePartOrgUnit(@PathVariable int domainId, @PathVariable String orgExternalKey,
                              @RequestBody OrgUnitRequestUpdateDTO orgUnitRequestDTO) throws Exception {

        OrgUnitEntity requestOrgUnitEntity = orgUnitRequestDTO.toOrgUnitEntity();
        requestOrgUnitEntity.setDomainId(domainId);
        requestOrgUnitEntity.setOrgExternalKey(orgExternalKey);

        orgUnitService.updatePartOrgUnit(requestOrgUnitEntity);
    }

    /**
     * 조직 이동.
     *
     * @param domainId : 조직이 속한 도메인 아이디.
     * @param orgExternalKey : 조직의 외부키.
     * @param orgUnitRequestDTO : 이동 정보를 담고 있는 객체
     */
    @PatchMapping("/domains/{domainId}/orgs/{orgExternalKey}/move")
    public void moveOrgUnit(@PathVariable int domainId, @PathVariable String orgExternalKey,
                            @RequestBody OrgUnitRequestMoveDTO orgUnitRequestDTO) throws Exception {

        String parentOrgExternalKey = orgUnitRequestDTO.getParentOrgExternalKey();
        String prevOrgExternalKey = orgUnitRequestDTO.getPrevOrgExternalKey();

        orgUnitService.moveOrgUnit(domainId, orgExternalKey, parentOrgExternalKey, prevOrgExternalKey);
    }

    /**
     * 조직 정보 삭제.
     *
     * @param domainId : 조직이 속한 도메인 외부키.
     * @param orgExternalKey : 조직의 외부키.
     */
    @DeleteMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public void deleteOrgUnit(@PathVariable int domainId, @PathVariable String orgExternalKey) throws Exception {

        orgUnitService.deleteOrgUnit(domainId, orgExternalKey);
    }
}
