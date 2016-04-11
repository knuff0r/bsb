package de.knuff0r.bsb.service;

import de.knuff0r.bsb.domain.GlobalSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * 
 * @author sebastian
 *
 */

@Component("globalSettingService")
@Transactional
class GlobalSettingServiceImpl implements GlobalSettingService {

	@Autowired
	private GlobalSettingRepository globalSettingRepository;

	public GlobalSettingServiceImpl() {
	}


	@Override
	public GlobalSetting save(GlobalSetting setting) {
		return globalSettingRepository.save(setting);
	}

	@Override
	public GlobalSetting getSetting(String name) {
		return globalSettingRepository.findOne(name);
	}

	@Override
	public void delete(GlobalSetting setting) {
		globalSettingRepository.delete(setting);

	}

	@Override
	public void delete(String name) {
		globalSettingRepository.delete(name);
	}

	@Override
	public Iterable<GlobalSetting> getAll() {
		return globalSettingRepository.findAll();
	}
}
