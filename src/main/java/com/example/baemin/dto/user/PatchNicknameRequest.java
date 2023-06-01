package com.example.baemin.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchNicknameRequest {

    @NotBlank(message = "nickname: {NotBlank}")
    private String nickname;

}
