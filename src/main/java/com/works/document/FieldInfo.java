package com.works.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldInfo {

    private String parameter;
    private String type;
    private String simpleType;
    private boolean required;
    private String description;

    private boolean isList;
    private boolean isModel;
}
