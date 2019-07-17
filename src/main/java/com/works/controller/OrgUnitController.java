package com.works.controller;

import com.works.dto.OrgUnitRequestCreateDTO;
import com.works.dto.OrgUnitResponseDTO;
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
     * @param   domainId 조직이 속한 도메인 아이디.
     * @param   orgExternalKey 조직의 외부키.
     * @return  조회한 OrgUnitDTO 객체.
     */
    @GetMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public OrgUnitResponseDTO getOrgUnitDTO(@PathVariable int domainId, @PathVariable String orgExternalKey) {

        return orgUnitService.getOrgUnitDTO(domainId, orgExternalKey);
    }

    /**
     * 조직 정보 추가.
     *
     * @param   domainId 조직이 속한 도메인 아이디.
     * @param   orgExternalKey 조직의 외부키.
     * @param   orgUnitRequestDTO 추가할 조직 정보를 담고 있는 객체.
     */
    @PostMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public void insertOrgUnitDTO(@PathVariable int domainId, @PathVariable String orgExternalKey,
                                 @RequestBody OrgUnitRequestCreateDTO orgUnitRequestDTO) {

        orgUnitRequestDTO.setDomainId(domainId);
        orgUnitRequestDTO.setOrgExternalKey(orgExternalKey);

        orgUnitService.insertOrgUnitDTO(orgUnitRequestDTO);
    }

    /**
     * 조직 정보 삭제.
     *
     * @param   domainId 조직이 속한 도메인 외부키.
     * @param   orgExternalKey 조직의 외부키.
     */
    @DeleteMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public void deleteOrgUnit(@PathVariable int domainId, @PathVariable String orgExternalKey) {

        orgUnitService.deleteOrgUnit(domainId, orgExternalKey);
    }
}