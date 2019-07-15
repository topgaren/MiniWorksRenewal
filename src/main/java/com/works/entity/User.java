package com.works.entity;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
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

    private LocalDateTime hireDate;
}
