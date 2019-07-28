package com.works.dto;

import com.works.annotation.DTO;
import com.works.annotation.DescriptionField;
import com.works.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DTO
public class UserRequestCreateDTO {

    @DescriptionField(description = "계정")
    private String account;

    @DescriptionField(description = "구성원 이름")
    private String userName;

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

    public UserEntity toUserEntity() {
        // UserEntity에만 존재하는 필드는 0 또는 null로 초기화.
        return new UserEntity(0, 0, account, userName, null, authentication,
                cellPhone, email, corporationAddress, hireDate);
    }
}
