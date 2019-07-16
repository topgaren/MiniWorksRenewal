package com.works.dto;

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
public class UserRequestCreateDTO {

    private int domainId;

    private String account;

    private String userName;

    private String userExternalKey;

    private int authentication;

    private String cellPhone;

    private String email;

    private String corporationAddress;

    private Date hireDate;

    public UserEntity toUserEntity() {
        // userId와 domainId는 디폴트 값(0)으로 초기화.
        return new UserEntity(0, domainId, account, userName, userExternalKey, authentication,
                cellPhone, email, corporationAddress, hireDate);
    }
}
