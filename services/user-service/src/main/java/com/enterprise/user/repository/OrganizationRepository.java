package com.enterprise.user.repository;

import com.enterprise.user.entity.Organization;
import com.enterprise.user.entity.OrganizationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepository
    extends JpaRepository<Organization, UUID>, JpaSpecificationExecutor<Organization> {

    boolean existsByName(String name);

    boolean existsByCode(String code);

    boolean existsByEmail(String email);

    boolean existsByNameAndIdNot(String name, UUID id);

    boolean existsByEmailAndIdNot(String email, UUID id);


    Optional<Organization> findByIdAndStatusNot(
        UUID id,
        OrganizationStatus status
    );

    Page<Organization> findByStatusNot(OrganizationStatus status, Pageable pageable);

}
