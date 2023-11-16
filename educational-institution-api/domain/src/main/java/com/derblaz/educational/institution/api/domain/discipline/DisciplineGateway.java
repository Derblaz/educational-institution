package com.derblaz.educational.institution.api.domain.discipline;

import com.derblaz.educational.institution.api.domain.Gateway;

import java.util.List;

public interface DisciplineGateway extends Gateway<Discipline, DisciplineID> {

    List<Discipline> findByNameAndActive(String term, int limit);
}
