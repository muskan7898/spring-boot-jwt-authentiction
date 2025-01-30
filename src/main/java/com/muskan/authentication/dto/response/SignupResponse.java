package com.muskan.authentication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupResponse {
    private Long id;
    private String username;
    private String password;
}
