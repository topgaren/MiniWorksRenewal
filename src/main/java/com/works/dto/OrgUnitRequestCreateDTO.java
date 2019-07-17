package com.works.dto;

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
public class OrgUnitRequestCreateDTO {

    private int domainId;

    @DescriptionField(description = "조직 이름")
    private String orgName;

    private String orgExternalKey;

    @DescriptionField(description = "조직 트리 깊이 (최소 깊이: 1)")
    private int depth;

    @DescriptionField(description = "동일 부모 조직을 갖는 형제 조직 간의 순서")
    private int siblingOrder;

    @DescriptionField(description = "부모 조직 아이디")
    private int parentId;

    @DescriptionField(description = "조직 소개")
    private String orgDescription;

    public OrgUnitEntity toOrgUnitEntity() {
        return new OrgUnitEntity(0, domainId, orgName, orgExternalKey, depth,
                siblingOrder, parentId, orgDescription);
    }
}
