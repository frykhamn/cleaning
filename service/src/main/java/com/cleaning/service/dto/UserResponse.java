package com.cleaning.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {

private String status;
private String message;
private UserDTO user;

}
