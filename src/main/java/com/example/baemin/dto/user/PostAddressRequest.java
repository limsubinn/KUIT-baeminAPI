package com.example.baemin.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class PostAddressRequest {

    @NotNull(message = "userId: {NotNull}")
    private long userId;

    @NotBlank(message = "defaultAddress: {NotBlank}")
    @Length(max = 100, message = "defaultAddress: 최대 {max}자까지 가능합니다")
    private String defaultAddress;

    @NotBlank(message = "detailAddress: {NotBlank}")
    @Length(max = 100, message = "detailAddress: 최대 {max}자까지 가능합니다")
    private String detailAddress;

    private String type;

    @NotNull(message = "x: {NotNull}")
    private long x;

    @NotNull(message = "y: {NotNull}")
    private long y;

}
