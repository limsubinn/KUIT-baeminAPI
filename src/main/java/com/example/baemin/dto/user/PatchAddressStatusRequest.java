package com.example.baemin.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchAddressStatusRequest {

    @NotBlank(message = "status: {NotBlank}")
    private String status;

}
