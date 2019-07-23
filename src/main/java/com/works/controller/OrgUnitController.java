package com.works.controller;

import com.works.annotation.DescriptionAPI;
import com.works.annotation.DescriptionAPIGroup;
import com.works.dto.OrgUnitRequestCreateDTO;
import com.works.dto.OrgUnitRequestMoveDTO;
import com.works.dto.OrgUnitRequestUpdateDTO;
import com.works.dto.OrgUnitResponseDTO;
import com.works.entity.OrgUnitEntity;
import com.works.service.OrgUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@DescriptionAPIGroup(apiGroupName = "Organization", apiGroupCode = 2)
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
    @RequestMapping(value = "/organization/domains/{domainId}/orgs/{orgExternalKey}", method = RequestMethod.GET)
    @DescriptionAPI(apiNameKorVer = "조직 단건 조회", description = "단일 조직 정보를 조회한다.", apiCode = 2001)
    public OrgUnitResponseDTO getOrgUnitDTO(@PathVariable(name = "domainId") int domainId,
                                            @PathVariable(name = "orgExternalKey") String orgExternalKey) throws Exception {

        return orgUnitService.getOrgUnitDTO(domainId, orgExternalKey);
    }

    /**
     * 조직 정보 추가.
     *
     * @param domainId : 조직이 속한 도메인 아이디.
     * @param orgExternalKey : 조직의 외부키.
     * @param orgUnitRequestDTO : 추가할 조직 정보를 담고 있는 객체.
     */
    @RequestMapping(value = "/organization/domains/{domainId}/orgs/{orgExternalKey}", method = RequestMethod.POST)
    @DescriptionAPI(apiNameKorVer = "조직 추가", description = "조직 정보를 새로 추가한다.", apiCode = 2002)
    public void insertOrgUnit(@PathVariable(name = "domainId") int domainId,
                              @PathVariable(name = "orgExternalKey") String orgExternalKey,
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
    @RequestMapping(value = "/organization/domains/{domainId}/orgs/{orgExternalKey}", method = RequestMethod.PUT)
    @DescriptionAPI(apiNameKorVer = "조직 수정", description = "조직 정보를 수정한다. 전달하지 않은 정보는 삭제된다.", apiCode = 2003)
    public void updateAllOrgUnit(@PathVariable(name = "domainId") int domainId,
                                 @PathVariable(name = "orgExternalKey") String orgExternalKey,
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
    @RequestMapping(value = "/organization/domains/{domainId}/orgs/{orgExternalKey}", method = RequestMethod.PATCH)
    @DescriptionAPI(apiNameKorVer = "조직 부분 수정", description = "조직 정보를 수정한다. 전달하지 않은 정보는 수정하지 않는다.", apiCode = 2004)
    public void updatePartOrgUnit(@PathVariable(name = "domainId") int domainId,
                                  @PathVariable(name = "orgExternalKey") String orgExternalKey,
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
    @RequestMapping(value = "/organization/domains/{domainId}/orgs/{orgExternalKey}/move", method = RequestMethod.PATCH)
    @DescriptionAPI(apiNameKorVer = "조직 이동", description = "조직을 이동시킨다. 이동 시 하위 조직도 같이 이동한다.", apiCode = 2005)
    public void moveOrgUnit(@PathVariable(name = "domainId") int domainId,
                            @PathVariable(name = "orgExternalKey") String orgExternalKey,
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
    @RequestMapping(value = "/organization/domains/{domainId}/orgs/{orgExternalKey}", method = RequestMethod.DELETE)
    @DescriptionAPI(apiNameKorVer = "조직 삭제", description = "조직 정보를 삭제한다.", apiCode = 2006)
    public void deleteOrgUnit(@PathVariable(name = "domainId") int domainId,
                              @PathVariable(name = "orgExternalKey") String orgExternalKey) throws Exception {

        orgUnitService.deleteOrgUnit(domainId, orgExternalKey);
    }
}
