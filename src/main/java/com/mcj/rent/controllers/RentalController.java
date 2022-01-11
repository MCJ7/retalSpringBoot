
package com.mcj.rent.controllers;

import com.mcj.rent.entities.*;
import java.time.LocalDate;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import com.mcj.rent.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author maxco
 */
@Controller
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService service;
    @Autowired
    private HouseService houseService;

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("")
    public ModelAndView listRental(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("rental");
        mav.addObject("title", "List of booking");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        mav.addObject("objects", service.list());
        mav.addObject("title", "List of booking");
        mav.addObject("btCreate", "Record booking");
        mav.addObject("emptyList", "Did not match any rentals");
        return mav;
    }

    /**
     *
     * @return
     */
    @GetMapping("/bookings")
    public ModelAndView personalListByGuest() {
        ModelAndView mav = new ModelAndView("rental");
        mav.addObject("title", "List of rentals");
        mav.addObject("objects", service.guestList());
        mav.addObject("btCreate", "Record booking");
        mav.addObject("emptyList", "Do not find any rental");
        return mav;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editRental(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("rentalForm");
        mav.addObject("object", service.searchId(id));
        mav.addObject("title", "Edit rental");
        mav.addObject("action", "update");
        return mav;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/create/{id}")
    public ModelAndView createRental(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("rentalForm");
        HouseEntity house = houseService.searchId(id);
        RentalEntity rental = new RentalEntity();
        rental.setHouse(house);
        mav.addObject("object", rental);
        mav.addObject("title", "Rental Registration Form");
        mav.addObject("action", "save");
        return mav;
    }

    /**
     *
     * @param bookingName
     * @param checkIn
     * @param checkOut
     * @param house
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public RedirectView saveRental(@RequestParam String bookingName,
            @RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut,
            @RequestParam String house,
            RedirectAttributes attributes) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            service.create(bookingName, checkIn, checkOut, house, userName);
            attributes.addFlashAttribute("exito-name", "Successful save");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e);
        }

        return new RedirectView("/rentals");
    }

    /**
     *
     * @param id
     * @param bookingName
     * @param checkIn
     * @param checkOut
     * @param house
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    public RedirectView modifyRental(@RequestParam String id, @RequestParam String bookingName,
            @RequestParam LocalDate checkIn, @RequestParam
                    LocalDate checkOut,@RequestParam String house, RedirectAttributes attributes) throws Exception {
        try {
            service.modify(id, bookingName, checkIn, checkOut);
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/rentals");
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/state/{id}")
    public RedirectView enableRental(@PathVariable String id, RedirectAttributes attributes) {
        try {
            service.enable(id);
            attributes.addFlashAttribute("exito-name", "Successful change state");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/rentals");
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/delete/{id}")
    public RedirectView deleteRental(@PathVariable String id, RedirectAttributes attributes) {
        try {
            service.delete(id);
            attributes.addFlashAttribute("exito-name", "Successful delete");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/rentals");
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/show/{id}")
    public ModelAndView showRentalDetails(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("houseDetail");
        mav.addObject("guests", service.guestList());
        for (RentalEntity rentalEntity : service.guestList()) {
            System.out.println(rentalEntity);
        }
        mav.addObject("object", service.searchId(id));
        mav.addObject("title", "Details of the house");
        mav.addObject("btMsg", "Booking");
        return mav;
    }
}
