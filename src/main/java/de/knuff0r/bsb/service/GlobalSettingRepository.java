package de.knuff0r.bsb.service;

import de.knuff0r.bsb.domain.GlobalSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author sebastian
 *
 */

@Repository
interface GlobalSettingRepository extends CrudRepository<GlobalSetting, String> {
	

}
