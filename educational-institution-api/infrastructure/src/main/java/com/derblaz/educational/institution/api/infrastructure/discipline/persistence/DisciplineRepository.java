package com.derblaz.educational.institution.api.infrastructure.discipline.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineRepository extends JpaRepository<DisciplineJpaEntity, String> {
    Page<DisciplineJpaEntity> findAll(Specification<DisciplineJpaEntity> whereClause, Pageable pageable);
}
