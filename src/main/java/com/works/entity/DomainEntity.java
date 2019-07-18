package com.works.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DomainEntity {

    private int domainId;

    private String domainExternalKey;

    private String domainAddress;

    private String domainDescription;
}
