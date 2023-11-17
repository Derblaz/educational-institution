package com.derblaz.educational.institution.api.infrastructure.user.persistence;

import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.domain.pagination.SearchQuery;
import com.derblaz.educational.institution.api.domain.user.User;
import com.derblaz.educational.institution.api.domain.user.UserGateway;
import com.derblaz.educational.institution.api.domain.user.UserID;
import com.derblaz.educational.institution.api.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.derblaz.educational.institution.api.infrastructure.utils.SpecificationUtils.like;

@Service
public class UserMySQLGateway implements UserGateway {

    private final UserRepository userRepository;

    public UserMySQLGateway(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    @Override
    public User create(User anUser) {
        return save(anUser);
    }

    @Override
    public void deleteById(UserID anId) {
        final var anIdValue = anId.getValue();
        if(this.userRepository.existsById(anIdValue)){
            this.userRepository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<User> findById(UserID anId) {
        return this.userRepository
                .findById(anId.getValue())
                .map(UserJpaEntity::toAggregate);
    }

    @Override
    public User update(User anUser) {
        return save(anUser);
    }

    @Override
    public Pagination<User> findAll(SearchQuery aQuery) {
        final var specifications = Optional.of(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(str -> SpecificationUtils.<UserJpaEntity>like("name", str)
                        .or(like("username", str))
                )
                .orElse(null);

        final var pageable = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var pageResult = this.userRepository.findAll(Specification.where(specifications), pageable);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(UserJpaEntity::toAggregate).toList()
        );
    }

    private User save(User anUser){
        return userRepository.save(UserJpaEntity.from(anUser)).toAggregate();
    }
}
