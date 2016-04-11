package de.knuff0r.bsb.service;

import de.knuff0r.bsb.domain.Order;

public interface OrderService {

    Order save(Order order);

    Iterable<Order> getAll();

    Iterable<Order> getCurrent();

    Iterable<Order> getFinished();

    Order getOrder(Long id);


}
