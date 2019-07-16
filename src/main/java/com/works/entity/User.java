package com.works.entity;

import com.works.dto.UserResponseDTO;
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
public class User {

    private int userId;

    private int domainId;

    private String account;

    private String userName;

    private String userExternalKey;

    private int authentication;

    private String cellPhone;

    private String email;

    private String corporationAddress;

    private Date hireDate;

    public UserResponseDTO toUserDTO() {

        return new UserResponseDTO(domainId, account, userName, userExternalKey, authentication,
                cellPhone, email, corporationAddress, hireDate);
    }
}
