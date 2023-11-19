package com.derblaz.educational.institution.api.infrastructure.course.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseJpaEntity, String>, JpaSpecificationExecutor<CourseJpaEntity> {
    Page<CourseJpaEntity> findAll(Specification<CourseJpaEntity> where, Pageable pageable);
}
