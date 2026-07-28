package com.enterprise.user.repository;


import com.enterprise.user.entity.Project;
import com.enterprise.user.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository
    extends JpaRepository<Project, UUID> {

    boolean existsByCode(String code);

    boolean existsByNameAndOrganizationId(
        String name,
        UUID organizationId
    );

    Optional<Project> findByIdAndStatusNot(
        UUID id,
        ProjectStatus status
    );
}
