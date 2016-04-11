package de.knuff0r.bsb.service;

import de.knuff0r.bsb.domain.Bluray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * 
 * @author sebastian
 *
 */

@Component("blurayService")
@Transactional
class BlurayServiceImpl implements BlurayService {

	private final BlurayRepositroy blurayRepositroy;


	@Autowired
	public BlurayServiceImpl(BlurayRepositroy blurayRepositroy) {
		this.blurayRepositroy = blurayRepositroy;
	}

	@Override
	public Bluray save(Bluray bluray) {
		if(bluray.getImdb()!=null && bluray.getImdb().equals(""))
			bluray.setImdb(null);
		return blurayRepositroy.save(bluray);
	}

	@Override
	public Iterable<Bluray> getAll() {
		return blurayRepositroy.findAll();
	}

	@Override
	public void delete(Bluray bluray) {
		blurayRepositroy.delete(bluray);
	}

	@Override
	public void delete(long id) {
		blurayRepositroy.delete(id);
	}

	@Override
	public Bluray getBluray(long id) {
		return blurayRepositroy.findOne(id);
	}




}
