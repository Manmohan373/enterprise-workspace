package com.enterprise.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrganizationRequest(

    @NotBlank
    String name,

    String email,

    String phone,

    String website,

    String address,

    Boolean active

) {
}
