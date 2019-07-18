package com.works.dto;

import com.works.annotation.DescriptionField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrgUnitRequestMoveDTO {

    @DescriptionField(description = "이동할 부모 조직의 외부키")
    private String parentOrgExternalKey;

    @DescriptionField(description = "바로 앞에 위치시킬 형제 조직의 외부키")
    private String prevOrgExternalKey;
}
