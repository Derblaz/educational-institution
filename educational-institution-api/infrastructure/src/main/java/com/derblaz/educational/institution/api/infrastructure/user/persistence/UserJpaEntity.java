package com.derblaz.educational.institution.api.infrastructure.user.persistence;

import com.derblaz.educational.institution.api.domain.user.Profile;
import com.derblaz.educational.institution.api.domain.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "user_profile")
public class UserJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "profile", nullable = false)
    private Profile profile;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "active", nullable = false)
    private boolean active;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    @Column(name = "deleted_at")
    private Instant deletedAt;

    public UserJpaEntity() {
    }

    public UserJpaEntity(String id,
                         String name,
                         Profile profile,
                         String username,
                         String password,
                         boolean active,
                         Instant createdAt,
                         Instant updatedAt,
                         Instant deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.username = username;
        this.password = password;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static UserJpaEntity from(final User aUser){
        return new UserJpaEntity(
                aUser.getId().getValue(),
                aUser.getName(),
                aUser.getProfile(),
                aUser.getUsername(),
                aUser.getPassword(),
                aUser.isActive(),
                aUser.getCreatedAt(),
                aUser.getUpdatedAt(),
                aUser.getDeletedAt()
        );
    }

    public User toAggregate(){
        return User.with(
                this.id,
                this.name,
                this.profile,
                this.username,
                this.password,
                this.active,
                this.createdAt,
                this.updatedAt,
                this.deletedAt
        );
    }
}
