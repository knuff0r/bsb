package de.knuff0r.bsb.service;

import de.knuff0r.bsb.domain.Order;
import de.knuff0r.bsb.domain.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author sebastian
 *
 */

@Repository
interface OrderRepositroy extends CrudRepository<Order,Long> {

	Iterable<Order> findByStatusIn(List<Status> status);

}
