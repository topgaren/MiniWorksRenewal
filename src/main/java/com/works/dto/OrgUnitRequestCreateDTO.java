package com.works.dto;

import com.works.entity.OrgUnitEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrgUnitRequestCreateDTO {

    private int domainId;

    private String orgName;

    private String orgExternalKey;

    private int depth;

    private int siblingOrder;

    private int parentId;

    private String orgDescription;

    public OrgUnitEntity toOrgUnitEntity() {
        return new OrgUnitEntity(0, domainId, orgName, orgExternalKey, depth,
                siblingOrder, parentId, orgDescription);
    }
}
