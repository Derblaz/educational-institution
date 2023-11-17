package com.derblaz.educational.institution.api.infrastructure.discipline.persistence;

import com.derblaz.educational.institution.api.domain.discipline.Discipline;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineID;
import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.domain.pagination.SearchQuery;
import com.derblaz.educational.institution.api.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.derblaz.educational.institution.api.infrastructure.utils.SpecificationUtils.*;

@Service
public class DisciplineMySQLGateway implements DisciplineGateway {

    private final DisciplineRepository disciplineRepository;

    public DisciplineMySQLGateway(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = Objects.requireNonNull(disciplineRepository);
    }

    @Override
    public Discipline create(Discipline aDiscipline) {
        return save(aDiscipline);
    }

    @Override
    public void deleteById(DisciplineID anId) {
        final var anIdValue = anId.getValue();
        if(this.disciplineRepository.existsById(anIdValue)){
            this.disciplineRepository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<Discipline> findById(DisciplineID anId) {
        return this.disciplineRepository
                .findById(anId.getValue())
                .map(DisciplineJpaEntity::toAggregate);
    }

    @Override
    public Discipline update(Discipline aDiscipline) {
        return save(aDiscipline);
    }

    @Override
    public Pagination<Discipline> findAll(SearchQuery aQuery) {
        final var specifications = Optional.of(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(str -> SpecificationUtils.<DisciplineJpaEntity>like("name", str))
                .orElse(null);

        final var pageable = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var pageResult = this.disciplineRepository.findAll(Specification.where(specifications), pageable);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(DisciplineJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public List<Discipline> findByNameAndActive(String term, int limit) {
        final var specifications = SpecificationUtils.<DisciplineJpaEntity>like("name", term)
                .and(active());

        final var pageable = PageRequest.of(
                0,
                limit,
                Sort.by(Sort.Direction.ASC, "name")
        );

        return this.disciplineRepository
                .findAll(Specification.where(specifications), pageable)
                .map(DisciplineJpaEntity::toAggregate)
                .toList();
    }

    private Discipline save(Discipline aDiscipline){
        return disciplineRepository.save(DisciplineJpaEntity.from(aDiscipline))
                .toAggregate();
    }
}
