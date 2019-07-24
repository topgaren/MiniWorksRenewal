package com.works.entity;

import com.works.dto.OrgUnitResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrgUnitEntity {

    private int orgId;

    private int domainId;

    private String orgName;

    private String orgExternalKey;

    private int depth;

    private int siblingOrder;

    private int parentId;

    private String orgDescription;

    public void updateOrgUnit(OrgUnitEntity addedOrgUnitEntity) {

        orgName = StringUtils.isEmpty(addedOrgUnitEntity.getOrgName()) ? orgName : addedOrgUnitEntity.getOrgName();
        orgExternalKey = StringUtils.isEmpty(addedOrgUnitEntity.getOrgExternalKey()) ? orgExternalKey : addedOrgUnitEntity.getOrgExternalKey();
        orgDescription = StringUtils.isEmpty(addedOrgUnitEntity.getOrgDescription()) ? orgDescription : addedOrgUnitEntity.getOrgDescription();
    }
}
