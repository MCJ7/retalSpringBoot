
package com.mcj.rent.controllers;

import com.mcj.rent.entities.GuestEntity;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import com.mcj.rent.services.GuestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

/**
 *
 * @author maxco
 */
@Controller
@RequestMapping("/guests")

public class GuestController {

    @Autowired
    private GuestService service;

    /**
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView listGuest(HttpServletRequest request) 
            throws InterruptedException {
        ModelAndView mav = new ModelAndView("guest");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        mav.addObject("objects", service.list());
        mav.addObject("title", "List of clients");
        mav.addObject("btCreate", "Record client");
        mav.addObject("emptyList", "Did not match any guests");
        return mav;

    }

    /**
     *
     * @return
     */
    @GetMapping("/create")
    public ModelAndView createGuest() {
        ModelAndView mav = new ModelAndView("guestForm");
        mav.addObject("object", new GuestEntity());
        mav.addObject("title", "Guest Registration Form");
        mav.addObject("action", "save");
        return mav;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editGuest(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("guestForm");
        mav.addObject("object", service.searchId(id));
        mav.addObject("title", "Edit client");
        mav.addObject("action", "update");
        return mav;
    }

    /**
     *
     * @param id
     * @param fullName
     * @param street
     * @param number
     * @param zipCode
     * @param city
     * @param country
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    public RedirectView modifyGuest(@RequestParam String id, @RequestParam 
            String fullName, @RequestParam String street, @RequestParam 
                    Integer number, @RequestParam String zipCode,
            @RequestParam String city, @RequestParam String country,
            RedirectAttributes attributes) throws Exception {
        try {
            service.modify(id, fullName, street, number, zipCode, city, country);
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/");
    }

    /**
     *
     * @param fullName
     * @param street
     * @param number
     * @param zipCode
     * @param city
     * @param country
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public RedirectView saveGuest(@RequestParam String fullName, 
            @RequestParam String street,
            @RequestParam Integer number, @RequestParam String zipCode,
            @RequestParam String city, @RequestParam String country,
            RedirectAttributes attributes) throws Exception {
        try {
            service.create(fullName, street, number, zipCode, city, country);
            attributes.addFlashAttribute("exito-name", "Successful save");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }

        return new RedirectView("/");
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/state/{id}")
    public RedirectView enableGuest(@PathVariable String id, 
            RedirectAttributes attributes) {
        try {
            service.enable(id);
            attributes.addFlashAttribute("exito-name", "Successful change of state");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/guests");
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/delete/{id}")
    public RedirectView deleteGuest(@PathVariable String id, 
            RedirectAttributes attributes) {
        try {
            service.delete(id);
            attributes.addFlashAttribute("exito-name", "Successful delete");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/guests");
    }

}
