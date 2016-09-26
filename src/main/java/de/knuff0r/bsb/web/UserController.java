package de.knuff0r.bsb.web;

import de.knuff0r.bsb.App;
import de.knuff0r.bsb.domain.User;
import de.knuff0r.bsb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String start(ModelMap model) {
        model.addAttribute("users", userService.getAll());
        return "admin/users";
    }

    @RequestMapping(value = "/admin/users/{user}/accept", method = RequestMethod.PUT)
    public String accept(Model model, @ModelAttribute User user) {
        App.log.info("user "+user.getUsername()+" successfully accpeted");
        user.setAccepted(true);
        userService.save(user);
        model.addAttribute("nonAccepted", userService.getNonAcceptedUsers());
        return "admin/index_user_table";
    }

    @RequestMapping(value = "/admin/users/{user}/deny", method = RequestMethod.PUT)
    public String deny(Model model, @ModelAttribute User user) {
        user.setAccepted(true);
        userService.save(user);
        model.addAttribute("nonAccepted", userService.getNonAcceptedUsers());
        return "admin/index_user_table";
    }



    @RequestMapping(value = "/admin/users/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id, Model model) {
        userService.delete(id);
        App.log.info("Delete User " + id);
        model.addAttribute("users", userService.getAll());
        return "admin/users_user_table";

    }

}
