package de.knuff0r.bsb.web;

import de.knuff0r.bsb.App;
import de.knuff0r.bsb.domain.*;
import de.knuff0r.bsb.service.BlurayService;
import de.knuff0r.bsb.service.GlobalSettingService;
import de.knuff0r.bsb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


@Controller
public class BlurayController {

    @Autowired
    BlurayService blurayService;

    @Autowired
    GlobalSettingService globalSettingService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/blurays", method = RequestMethod.GET)
    public String getBlurays(ModelMap model, @ModelAttribute Bluray bluray) {
        model.addAttribute("blurays", blurayService.getAll());
        return "blurays";
    }

    @RequestMapping(value = "/admin/bluray", method = RequestMethod.POST)
    public String addBluray(RedirectAttributes redirect, @Valid Bluray bluray) {
        blurayService.save(bluray);
        redirect.addFlashAttribute("add", true);
        return "redirect:/blurays";
    }

    @RequestMapping(value = "/bluray/image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getBluray(HttpServletResponse response, @RequestParam String surl) {
        URL url = null;
        try {
            url = new URL(surl);
        } catch (MalformedURLException e) {
            App.log.error(e.getMessage());
        }
        InputStream in = null;
        try {
            in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] bytes = out.toByteArray();
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
        } catch (IOException e) {
            App.log.error(e.getMessage());
        }
        return null;

    }

    @RequestMapping(value = "/bluray/{bluray}", method = RequestMethod.GET)
    public String getBluray(Model model, @ModelAttribute Bluray bluray, @ModelAttribute Order order, @ModelAttribute Ticket ticket) {
        RestTemplate restTemplate = new RestTemplate();
        ImdbMovie movie;
        if (bluray.getImdb() == null)
            movie = restTemplate.getForObject("http://www.omdbapi.com/?t=" + bluray.getTitle() + "&plot=short&r=json", ImdbMovie.class);
        else
            movie = restTemplate.getForObject("http://www.omdbapi.com/?i=" + bluray.getImdb() + "&plot=short&r=json", ImdbMovie.class);
        model.addAttribute("movie", movie);
        model.addAttribute("surCharge", Float.parseFloat(globalSettingService.getSetting("premiumSurcharge").getValue()));
        switch (bluray.getPriceRange()) {
            case SINGLE:
                model.addAttribute("price", Float.parseFloat(globalSettingService.getSetting("singlePrice").getValue()));
                break;
            case DOUBLE:
                model.addAttribute("price", Float.parseFloat(globalSettingService.getSetting("doublePrice").getValue()));
                break;
            default:
                model.addAttribute("price", "No price information available");
        }

        return "bluray";
    }

    @RequestMapping(value = "/bluray/{bluray}/ticket", method = RequestMethod.POST)
    public String addTicket(RedirectAttributes redirect, @Valid Ticket ticket, @ModelAttribute Bluray bluray) {
        ticket.setUser(getCurrentUser());
        bluray.addTicket(ticket);
        blurayService.save(bluray);
        redirect.addFlashAttribute("add_ticket", true);
        App.log.info("Adding ticket for Bluray " + bluray.getTitle() + " by User " + getCurrentUser());
        return "redirect:/bluray/" + bluray.getId();
    }


    @RequestMapping(value = "/admin/bluray/{id}", method = RequestMethod.DELETE)
    public String deleteBluray(@PathVariable long id, RedirectAttributes redirect) {
        blurayService.delete(id);
        redirect.addFlashAttribute("delete", true);
        return "redirect:/blurays";
    }

    @RequestMapping(value = "/admin/bluray/{bluray}", method = RequestMethod.PUT)
    public String putBluray(RedirectAttributes redirect, @ModelAttribute @Valid Bluray bluray) {
        App.log.info("Bluray updated: " + bluray.getTitle());
        blurayService.save(bluray);
        redirect.addFlashAttribute("update", true);
        return "redirect:/blurays/";
    }

    private User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userService.getUserByUsername(principal.getUsername());
    }
}
