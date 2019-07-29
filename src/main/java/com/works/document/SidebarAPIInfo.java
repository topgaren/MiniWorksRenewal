package com.works.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SidebarAPIInfo {

    private String apiNameKorVer;

    private int apiCode;

    public SidebarAPIInfo(String apiNameKorVer, int apiCode) {

        this.apiNameKorVer = apiNameKorVer;
        this.apiCode = apiCode;
    }
}
