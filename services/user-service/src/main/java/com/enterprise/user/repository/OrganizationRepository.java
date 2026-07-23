package com.enterprise.user.repository;

import com.enterprise.user.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationRepository
    extends JpaRepository<Organization, UUID> {

    boolean existsByName(String name);

    boolean existsByCode(String code);

    boolean existsByEmail(String email);


}
