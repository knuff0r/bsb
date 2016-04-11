package de.knuff0r.bsb.service;

import de.knuff0r.bsb.domain.Bluray;

public interface BlurayService {

    Bluray save(Bluray bluray);

    Bluray getBluray(long id);

    void delete(Bluray bluray);

    void delete(long id);

    Iterable<Bluray> getAll();


}
