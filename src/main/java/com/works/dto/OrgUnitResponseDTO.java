package com.works.dto;

import com.works.annotation.DTO;
import com.works.annotation.DescriptionField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DTO
public class OrgUnitResponseDTO {

    @DescriptionField(description = "조직 아이디")
    private int orgId;

    @DescriptionField(description = "도메인 아이디")
    private int domainId;

    @DescriptionField(description = "조직 이름")
    private String orgName;

    @DescriptionField(description = "조직 외부키")
    private String orgExternalKey;

    @DescriptionField(description = "조직 트리 깊이 (최소 깊이: 1)")
    private int depth;

    @DescriptionField(description = "동일 부모 조직을 갖는 형제 조직 간의 순서")
    private int siblingOrder;

    @DescriptionField(description = "부모 조직 아이디")
    private int parentId;

    @DescriptionField(description = "조직 소개")
    private String orgDescription;
}
