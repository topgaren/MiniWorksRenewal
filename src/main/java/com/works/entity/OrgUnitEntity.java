package com.works.entity;

import com.works.dto.OrgUnitResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public OrgUnitResponseDTO toOrgUnitDTO() {
        return new OrgUnitResponseDTO(orgId, domainId, orgName, orgExternalKey,
                depth, siblingOrder, parentId, orgDescription);
    }
}
