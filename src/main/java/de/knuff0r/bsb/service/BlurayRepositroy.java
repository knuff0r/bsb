package de.knuff0r.bsb.service;

import de.knuff0r.bsb.domain.Bluray;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author sebastian
 *
 */

@Repository
interface BlurayRepositroy extends CrudRepository<Bluray,Long> {
	

}
