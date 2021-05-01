package com.example.email.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author Khairul Islam Azam
 * @since 1.0.0
 */
@Getter
@Setter
public class TemplateInfo {

    private String templateName;
    private Map<String,Object> templateModel;

}
