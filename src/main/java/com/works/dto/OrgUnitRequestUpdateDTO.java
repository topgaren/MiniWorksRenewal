package com.works.dto;

import com.works.annotation.DTO;
import com.works.annotation.DescriptionField;
import com.works.entity.OrgUnitEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DTO
public class OrgUnitRequestUpdateDTO {

    @DescriptionField(description = "조직 이름")
    private String orgName;

    @DescriptionField(description = "부모 조직 외부키")
    private String parentOrgExternalKey;

    @DescriptionField(description = "조직 소개")
    private String orgDescription;

    public OrgUnitEntity toOrgUnitEntity() {
        return new OrgUnitEntity(0, 0, orgName, null, 1,
                0, 0, orgDescription);
    }
}
