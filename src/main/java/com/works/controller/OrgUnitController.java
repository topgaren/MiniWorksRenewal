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

    @GetMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public OrgUnitResponseDTO getOrgUnitDTO(@PathVariable int domainId, @PathVariable String orgExternalKey) {

        return orgUnitService.getOrgUnitDTO(domainId, orgExternalKey);
    }

    @PostMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public void insertOrgUnitDTO(@PathVariable int domainId, @PathVariable String orgExternalKey,
                                 @RequestBody OrgUnitRequestCreateDTO orgUnitRequestDTO) {

        orgUnitRequestDTO.setDomainId(domainId);
        orgUnitRequestDTO.setOrgExternalKey(orgExternalKey);

        orgUnitService.insertOrgUnitDTO(orgUnitRequestDTO);
    }

    @DeleteMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public void deleteOrgUnit(@PathVariable int domainId, @PathVariable String orgExternalKey) {

        orgUnitService.deleteOrgUnit(domainId, orgExternalKey);
    }
}
