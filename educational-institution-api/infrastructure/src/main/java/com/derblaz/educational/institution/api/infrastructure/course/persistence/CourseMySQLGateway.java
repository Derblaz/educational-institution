package com.derblaz.educational.institution.api.infrastructure.course.persistence;

import com.derblaz.educational.institution.api.domain.course.Course;
import com.derblaz.educational.institution.api.domain.course.CourseGateway;
import com.derblaz.educational.institution.api.domain.course.CourseID;
import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.domain.pagination.SearchQuery;
import com.derblaz.educational.institution.api.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CourseMySQLGateway implements CourseGateway {

    private final CourseRepository courseRepository;

    public CourseMySQLGateway(CourseRepository courseRepository) {
        this.courseRepository = Objects.requireNonNull(courseRepository);
    }

    @Override
    public Course create(Course aCourse) {
        return save(aCourse);
    }

    @Override
    public void deleteById(CourseID anId) {
        final var anIdValue = anId.getValue();
        if(this.courseRepository.existsById(anIdValue)){
            this.courseRepository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<Course> findById(CourseID anId) {
        return this.courseRepository
                .findById(anId.getValue())
                .map(CourseJpaEntity::toAggregate);
    }

    @Override
    public Course update(Course aCourse) {
        return save(aCourse);
    }

    @Override
    public Pagination<Course> findAll(SearchQuery aQuery) {
        final var specifications = Optional.of(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(str -> SpecificationUtils.<CourseJpaEntity>like("name", str))
                .orElse(null);

        final var pageable = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var pageResult = this.courseRepository.findAll(Specification.where(specifications), pageable);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CourseJpaEntity::toAggregate).toList()
        );
    }

    private Course save(Course aCourse){
        return courseRepository.save(CourseJpaEntity.from(aCourse))
                .toAggregate();
    }
}
