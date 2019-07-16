package com.works.controller;

import com.works.dto.OrgUnitResponseDTO;
import com.works.service.OrgUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OrgUnitController {

    @Autowired
    OrgUnitService orgUnitService;

    @GetMapping("/domains/{domainId}/orgs/{orgExternalKey}")
    public OrgUnitResponseDTO getOrgUnitDTO(@PathVariable int domainId, @PathVariable String orgExternalKey) {

        return orgUnitService.getOrgUnitDTO(domainId, orgExternalKey);
    }
}
