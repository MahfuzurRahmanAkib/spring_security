package com.jwt.example.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private Long id;
    @Size(max = 250)
    @NotBlank
    private String name;
    @Size(max = 250)
    @NotBlank
    private String email;
    @Size(max = 50)
    @NotBlank
    private String password;
    @Size(max = 250)
    private String about;
}
