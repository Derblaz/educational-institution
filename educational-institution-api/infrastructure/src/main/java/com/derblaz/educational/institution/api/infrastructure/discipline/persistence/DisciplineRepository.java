package com.derblaz.educational.institution.api.infrastructure.discipline.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DisciplineRepository extends JpaRepository<DisciplineJpaEntity, String> {
    List<DisciplineJpaEntity> findByIdIn(Collection<String> ids);
    Page<DisciplineJpaEntity> findAll(Specification<DisciplineJpaEntity> whereClause, Pageable pageable);
}
