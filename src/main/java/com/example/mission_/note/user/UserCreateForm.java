package com.example.mission_.note.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCreateForm {

    @NotEmpty(message = "사용자 ID 를 입력해주세요.")
    @Size(min = 3, max = 20)
    private String username;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password1;

    @NotEmpty(message = "비밀번호를 확인해주세요.")
    private String password2;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email
    private String email;
}
