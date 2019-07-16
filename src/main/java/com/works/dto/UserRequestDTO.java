package com.works.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequestDTO {

    private int domainId;

    private String account;

    private String userName;

    private String userExternalKey;

    private int authentication;

    private String cellPhone;

    private String email;

    private String corporationAddress;

    private Date hireDate;

    public User toUserEntity() {
        return new User(0, domainId, account, userName, userExternalKey, authentication,
                cellPhone, email, corporationAddress, hireDate);
    }
}
