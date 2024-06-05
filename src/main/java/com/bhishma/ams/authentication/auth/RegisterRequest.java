package com.bhishma.ams.authentication.auth;

import com.bhishma.ams.authentication.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String userId;
  private String password;
  private Role role;
  private String mail;
}
