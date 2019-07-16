package com.works.dto;

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
public class UserResponseDTO {

    private int domainId;

    private String account;

    private String userName;

    private String userExternalKey;

    private int authentication;

    private String cellPhone;

    private String email;

    private String corporationAddress;

    private Date hireDate;
}
