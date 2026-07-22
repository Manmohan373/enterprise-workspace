package com.enterprise.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateOrganizationRequest(

    @NotBlank
    String name,

    @NotBlank
    String code,

    String email,

    String phone,

    String website,

    String address

) {
}
