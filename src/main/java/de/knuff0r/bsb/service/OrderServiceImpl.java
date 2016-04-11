package de.knuff0r.bsb.service;

import de.knuff0r.bsb.domain.Order;
import de.knuff0r.bsb.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sebastian
 */
@Component("orderService")
@Transactional
class OrderServiceImpl implements OrderService {

    private final OrderRepositroy orderRepositroy;


    @Autowired
    public OrderServiceImpl(OrderRepositroy orderRepositroy) {
        this.orderRepositroy = orderRepositroy;
    }

    @Override
    public Order save(Order order) {
        return orderRepositroy.save(order);
    }

    @Override
    public Iterable<Order> getAll() {
        return orderRepositroy.findAll();
    }

    private List<Status> getAcceptingStates() {
        List<Status> stati = new ArrayList<>();
        stati.add(Status.PAID_AND_DELIVERED);
        stati.add(Status.CANCELED);
        return stati;
    }

    private List<Status> getNonAcceptingStates() {
        List<Status> stati = new ArrayList<Status>();
        stati.add(Status.WAITING_FOR_ACCEPTANCE);
        stati.add(Status.WAITING_FOR_CANCEL);
        stati.add(Status.IN_PROGRESS);
        stati.add(Status.READY_FOR_COLLECTION);
        return stati;
    }

    @Override
    public Iterable<Order> getCurrent() {
        return orderRepositroy.findByStatusIn(getNonAcceptingStates());
    }

    @Override
    public Iterable<Order> getFinished() {
        return orderRepositroy.findByStatusIn(getAcceptingStates());
    }


    @Override
    public Order getOrder(Long id) {
        return orderRepositroy.findOne(id);
    }


}
