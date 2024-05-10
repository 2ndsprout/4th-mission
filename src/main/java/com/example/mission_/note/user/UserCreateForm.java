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

    @Size(min = 3, max = 25, message = "3~25 자 내외로 입력해주세요")
    @NotEmpty(message = "아이디를 입력해주세요")
    private String username;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password1;

    @NotEmpty(message = "비밀번호를 다시 한번 확인 해주세요")
    private String password2;

    @NotEmpty(message = "닉네임을 입력해주세요")
    private String nickname;

    @Email
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;
}
