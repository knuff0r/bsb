package de.knuff0r.bsb.web;

import de.knuff0r.bsb.App;
import de.knuff0r.bsb.domain.Bluray;
import de.knuff0r.bsb.domain.Order;
import de.knuff0r.bsb.domain.User;
import de.knuff0r.bsb.service.BlurayService;
import de.knuff0r.bsb.service.OrderService;
import de.knuff0r.bsb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class OrdersController {

    @Autowired
    OrderService orderService;

    @Autowired
    BlurayService blurayService;

    @Autowired
    UserService userService;

    private User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userService.getUserByUsername(principal.getUsername());
    }

    @RequestMapping("/orders")
    public String start(ModelMap model) {
        User user = getCurrentUser();
        if (user.isAdmin()) {

            model.addAttribute("currentOrders", orderService.getCurrent());
            model.addAttribute("finishedOrders", orderService.getFinished());
            return "admin/orders";
        }

        model.addAttribute("orders", user.getOrders());
        return "user/orders";


    }

    /*
    @RequestMapping("/admin/orders/set-status")
    public String setStatus(RedirectAttributes redirect,
                            @RequestParam("order_id") Long id,
                            @RequestParam("order_status") String status) {
        Order o = orderService.getOrder(id);
        switch (status) {
            case "WAITING_FOR_ACCEPTANCE":
                o.setStatus(Status.IN_PROGRESS);
                break;
            case "IN_PROGRESS":
                o.setStatus(Status.READY_FOR_COLLECTION);
                break;
            case "READY_FOR_COLLECTION0":
                o.setStatus(Status.PAID_AND_DELIVERED);
                break;
            case "WAITING_FOR_CANCEL":
                o.setStatus(Status.IN_PROGRESS);
                break;
            case "READY_FOR_COLLECTION1":
                o.setStatus(Status.DELIVERED);
                break;
            case "DELIVERED":
                o.setStatus(Status.RETURNED);
                break;
            default:
                break;
        }
        orderService.save(o);
        return "redirect:/admin/orders";
    }


    @RequestMapping("/admin/orders/set-cancel")
    public String setCancel(RedirectAttributes redirect,
                            @RequestParam("order_id") Long id) {
        Order o = orderService.getOrder(id);
        o.setStatus(Status.CANCELED);
        orderService.save(o);
        return "redirect:/admin/orders";
    }

    @RequestMapping("/orders/set-status")
    public String setstatususer(RedirectAttributes redirect,
                                @RequestParam("order_id") Long id, @RequestParam("order_status") String status) {
        Order o = orderService.getOrder(id);
        switch (status) {
            case "WAITING_FOR_ACCEPTANCE":
                o.setStatus(Status.CANCELED);
                break;
            case "WAITING_FOR_AVAILABLE":
                o.setStatus(Status.CANCELED);
                break;
            case "IN_PROGRESS":
                o.setStatus(Status.WAITING_FOR_CANCEL);
                break;
            case "READY_FOR_COLLECTION":
                o.setStatus(Status.CANCELED);
                break;
            default:
                break;
        }
        orderService.save(o);
        return "redirect:/cart";
    }

*/
    @RequestMapping(value = "/bluray/{bluray}/order", method = RequestMethod.POST)
    public String addOrder(RedirectAttributes redirect, @ModelAttribute Bluray bluray, @ModelAttribute @Valid Order order) {
        App.log.info("new Order :"+order.getPrice());
        order.setBluray(bluray);
        User user = getCurrentUser();
        user.addOrder(order);
        order.setUser(user);
        userService.save(user);
        redirect.addFlashAttribute("addToCart", true);
        return "redirect:/blurays";
    }


}
