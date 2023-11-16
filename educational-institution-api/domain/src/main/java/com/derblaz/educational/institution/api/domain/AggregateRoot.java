package com.derblaz.educational.institution.api.domain;

import java.time.Instant;
import java.util.Objects;

public abstract class AggregateRoot<T, ID extends Identifier> extends Entity<ID> {

    protected boolean active;
    protected Instant createdAt;
    protected Instant updatedAt;
    protected Instant deletedAt;

    protected AggregateRoot(final ID id, boolean active, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        super(id);
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        this.deletedAt = deletedAt;
    }

    private T self(){
        return (T) this;
    }

    public T activate(){
        if(isActive()) return self();

        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return self();
    }

    public T deactivate(){
        if(!isActive()) return self();

        this.deletedAt = Instant.now();
        this.active = false;
        this.updatedAt = Instant.now();
        return self();
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
