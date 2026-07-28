package com.enterprise.user.repository;

import com.enterprise.user.entity.Team;
import com.enterprise.user.entity.TeamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository
    extends JpaRepository<Team, UUID> {

    boolean existsByCode(String code);

    boolean existsByNameAndProjectId(
        String name,
        UUID projectId
    );

    Optional<Team> findByIdAndStatusNot(
        UUID id,
        TeamStatus status
    );
}
