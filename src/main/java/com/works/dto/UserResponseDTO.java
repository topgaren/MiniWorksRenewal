package com.works.dto;

import com.works.annotation.DTO;
import com.works.annotation.DescriptionField;
import com.works.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DTO
public class UserResponseDTO {

    @DescriptionField(description = "도메인 아이디")
    private int domainId;

    @DescriptionField(description = "계정")
    private String account;

    @DescriptionField(description = "구성원 이름")
    private String userName;

    @DescriptionField(description = "구성원 외부키")
    private String userExternalKey;

    @DescriptionField(description = "권한")
    private int authentication;

    @DescriptionField(description = "휴대폰 번호")
    private String cellPhone;

    @DescriptionField(description = "개인 이메일 주소")
    private String email;

    @DescriptionField(description = "회사 주소")
    private String corporationAddress;

    @DescriptionField(description = "입사일 (형태: YYYY-MM-dd)")
    private Date hireDate;

    public UserResponseDTO(UserEntity userEntity) {
        this.domainId = userEntity.getDomainId();
        this.account = userEntity.getAccount();
        this.userName = userEntity.getUserName();
        this.userExternalKey = userEntity.getUserExternalKey();
        this.authentication = userEntity.getAuthentication();
        this.cellPhone = userEntity.getCellPhone();
        this.email = userEntity.getEmail();
        this.corporationAddress = userEntity.getCorporationAddress();
        this.hireDate = userEntity.getHireDate();
    }
}
