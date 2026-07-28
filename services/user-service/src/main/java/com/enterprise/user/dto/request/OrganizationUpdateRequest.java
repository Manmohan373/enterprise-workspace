package com.enterprise.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrganizationUpdateRequest(

    @NotBlank(message = "Organization name is required")
    @Size(min = 3, max = 100)
    String name,

    @NotBlank(message = "Organization email is required")
    @Email
    String email,

    String phone,

    String website,

    @Size(max = 500)
    String description
) {
}
