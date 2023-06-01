package com.example.baemin.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class PatchNicknameRequest {

    @NotBlank(message = "nickname: {NotBlank}")
    @Length(max = 50, message = "nickname: 최대 {max}자까지 가능합니다")
    private String nickname;

}
