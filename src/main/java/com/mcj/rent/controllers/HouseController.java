package com.mcj.rent.controllers;


import com.mcj.rent.entities.HouseEntity;
import com.mcj.rent.enums.TypePropertyEnum;
import com.mcj.rent.services.*;
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

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author maxco
 */
@Controller
@RequestMapping("/houses")
public class HouseController {

    @Autowired
    private HouseService service;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    
    /**
     *
     * @param request
     * @return
     */
    @GetMapping("")
    public ModelAndView listHouse(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("houseWithPic");
        mav.addObject("title", "List of houses");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        mav.addObject("user", userService.session());
        mav.addObject("objects", service.list());
        mav.addObject("btCreate", "Record house");
        mav.addObject("emptyList", "Did not match any houses");
        return mav;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editHouse(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("houseForm");
        mav.addObject("object", service.searchId(id));
        mav.addObject("typeHouses", TypePropertyEnum.values());
        mav.addObject("action", "update");

        return mav;
    }

    /**
     *
     * @return
     */
    @GetMapping("/create")
    public ModelAndView createHouse() {
        ModelAndView mav = new ModelAndView("houseForm");
        mav.addObject("object", new HouseEntity());
        mav.addObject("typeHouses", TypePropertyEnum.values());
        mav.addObject("title", "House Registration Form");
        mav.addObject("action", "save");
        return mav;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/show/{id}")
    public ModelAndView detailHouse(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("houseDetail");
        mav.addObject("comments", commentService.findByHouse(service.searchId(id)));
        mav.addObject("object", service.searchId(id));
        mav.addObject("title", "Details of house");
        mav.addObject("btMsg", "Book");
        return mav;
    }

    /**
     *
     * @param pic
     * @param street
     * @param number
     * @param zipCode
     * @param toDate
     * @param city
     * @param country
     * @param sinceDate
     * @param numberOfRooms
     * @param numberOfBaths
     * @param timeMin
     * @param timeMax
     * @param price
     * @param typeHouse
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public RedirectView daveHouse(MultipartFile pic,@RequestParam String street, 
            @RequestParam Integer number, @RequestParam String zipCode,
            @RequestParam LocalDate toDate, @RequestParam String city, 
            @RequestParam String country,@RequestParam LocalDate sinceDate,
            @RequestParam Integer numberOfRooms,@RequestParam Integer 
            numberOfBaths,@RequestParam Integer timeMin,@RequestParam Integer 
            timeMax, @RequestParam Double price,@RequestParam String typeHouse,
            RedirectAttributes attributes) throws Exception {
        try {
            service.create(pic,street, number, zipCode, country, sinceDate, 
            toDate,numberOfRooms,numberOfBaths, timeMin, timeMax, price, typeHouse, city);
            attributes.addFlashAttribute("exito-name", "Successful save");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }

        return new RedirectView("/");
    }

    /**
     *
     * @param pic
     * @param id
     * @param street
     * @param number
     * @param zipCode
     * @param toDate
     * @param numberOfRooms
     * @param numberOfBaths
     * @param city
     * @param country
     * @param sinceDate
     * @param timeMin
     * @param timeMax
     * @param price
     * @param typeHouse
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    public RedirectView modifyHouse(MultipartFile pic,@RequestParam String id, 
            @RequestParam String street, @RequestParam Integer number, 
            @RequestParam String zipCode,@RequestParam LocalDate toDate,
            @RequestParam Integer numberOfRooms,@RequestParam Integer 
            numberOfBaths, @RequestParam String city, @RequestParam String country,
            @RequestParam LocalDate sinceDate, @RequestParam Integer timeMin,
            @RequestParam Integer timeMax, @RequestParam Double price, 
            @RequestParam String typeHouse,
            RedirectAttributes attributes) throws Exception {
        try {
            service.modify(pic,id, street, number, zipCode, country, sinceDate, 
                    toDate,numberOfRooms,numberOfBaths, timeMin, timeMax, price, typeHouse, city);
            attributes.addFlashAttribute("exito-name", "Successful update");
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
    public RedirectView enableHouse(@PathVariable String id, RedirectAttributes attributes) {
        try {
            service.enable(id);
            attributes.addFlashAttribute("exito-name", "Successful change of state");
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
    @PostMapping("/delete/{id}")
    public RedirectView deleteHouse(@PathVariable String id, RedirectAttributes attributes) {
        try {
            service.delete(id);
            attributes.addFlashAttribute("exito-name", "Successful delete");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/");
    }

}
