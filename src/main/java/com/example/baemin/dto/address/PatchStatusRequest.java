package com.example.baemin.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchStatusRequest {

    @NotBlank(message = "status: {NotBlank}")
    private String status;

}
