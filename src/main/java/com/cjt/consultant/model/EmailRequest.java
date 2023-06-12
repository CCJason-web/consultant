package com.cjt.consultant.model;

import lombok.Data;

@Data
public class EmailRequest {
    private String code;
    private String email;
}
