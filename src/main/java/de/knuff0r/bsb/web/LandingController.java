package de.knuff0r.bsb.web;


import de.knuff0r.bsb.App;
import de.knuff0r.bsb.app.SmtpMailSender;
import de.knuff0r.bsb.domain.Bluray;
import de.knuff0r.bsb.domain.GlobalSetting;
import de.knuff0r.bsb.domain.User;
import de.knuff0r.bsb.service.BlurayService;
import de.knuff0r.bsb.service.GlobalSettingService;
import de.knuff0r.bsb.service.OrderService;
import de.knuff0r.bsb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;


@Controller
public class LandingController {

    @Autowired
    private UserService userService;

    @Autowired
    private GlobalSettingService globalSettingService;

    @Autowired
    private BlurayService blurayService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SmtpMailSender mailSender;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Model model) {

        createTestData();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            org.springframework.security.core.userdetails.User userD =
                    (org.springframework.security.core.userdetails.User) principal;
            User user = userService.getUserByUsername(userD.getUsername());
            if (user.isAdmin()) {
                model.addAttribute("nonAccepted", userService.getNonAcceptedUsers());
                model.addAttribute("name", user.getUsername());
                return "admin/index";
            }
            model.addAttribute("user",user);
            return "user/index";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(RedirectAttributes redirect, @Valid User user, HttpServletRequest request,
                           HttpServletResponse response, ServletContext context, Locale locale) {


        try {
            mailSender.send(user, "Registration", "mail_register", request, response, context, locale);
            redirect.addFlashAttribute("mailsend", true);
        } catch (MessagingException e) {
            App.log.error(e.getMessage());
            redirect.addFlashAttribute("mailsend", false);
            return "redirect:/";
        }

        App.log.info("Trying to register User: " + user.toString());

        userService.save(userService.createUser(user));

        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activate/{key}")
    public String activateAccount(RedirectAttributes redirect, @PathVariable String key) {
        App.log.info("Trying to activate account with key: " + key);
        User user = userService.getUserByKey(key);
        if (user != null) {
            user.setActive(true);
            userService.save(user);
            redirect.addFlashAttribute("activated", true);
        }
        return "redirect:/";
    }


    private void createTestData() {
        if (userService.count() == 0) {

            globalSettingService.save(new GlobalSetting("singlePrice","3.50"));
            globalSettingService.save(new GlobalSetting("doublePrice","3.50"));
            globalSettingService.save(new GlobalSetting("premiumSurcharge","1.5"));

            User admin = userService.createUser(new User("master@knuff0r.xyz", "master", true));
            admin.setUsername("admin");

            User u = userService.createUser(new User("user@knuff0r.xyz", "1234", "user"));
            u.setActive(true);
            for (int i = 0; i < 10; i++) {
                User us = new User("user-gen" + i + "@gen.xyz", "1234");
                us.setActive(true);
                userService.save(us);
            }
            Bluray b = new Bluray("I, Robot");
            Bluray b1 = new Bluray("Who Am I");
            Bluray b2 = new Bluray("I am Legend");


            b.addLanguage(Bluray.Language.ENGLISH);
            b.setD3(true);

            userService.save(admin);
            userService.save(u);
            blurayService.save(b);
            blurayService.save(b1);
            blurayService.save(b2);

        }
    }

}
