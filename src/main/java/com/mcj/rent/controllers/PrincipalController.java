
package com.mcj.rent.controllers;

import com.mcj.rent.entities.UserEntity;
import com.mcj.rent.enums.RoleEnum;
import com.mcj.rent.services.*;
import java.security.Principal;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author maxco
 */
@Controller
@RequestMapping("")
public class PrincipalController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private GuestService guestService;
    @Autowired
    private HostService hostService;
    @Autowired
    private HouseService houseService;

    @GetMapping("")
    public ModelAndView generalPanel(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("Index");
        mav.addObject("titlePage", "List of houses");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        mav.addObject("objects", houseService.list());
        mav.addObject("title", "Find the house what you search");
        mav.addObject("emptyList", "Did not match any houses");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, 
            @RequestParam(required = false) String logout, Principal principal, 
            HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("login");
        mav.addObject("title", "Log into Rental");
        mav.addObject("titlePage", "Log into ");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("logout", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        if (error != null) {
            mav.addObject("error", "user or password is incorrect");
        }

        if (logout != null) {
            mav.addObject("logout", "Has been closed the session");
        }

        if (principal != null) {
            LOGGER.info("Principal -> {}", principal.getName());
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    @PostMapping("/save")
    public RedirectView userSave(MultipartFile avatar,@RequestParam 
            String userName, @RequestParam String email,
            @RequestParam String pass, @RequestParam String role,
            RedirectAttributes attributes) throws Exception {
        try {

            userService.create(avatar,userName, email, pass, role.toUpperCase());
            attributes.addFlashAttribute("exito-name", "Successful save");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        if (role.equalsIgnoreCase("guest")) {
            return new RedirectView("/guests/create");
        } else if (role.equalsIgnoreCase("host")) {
            return new RedirectView("/hosts/create");
        } else {
            return new RedirectView("/login");
        }
    }
    @PostMapping("/saveChanges")
    public RedirectView savingChanges(MultipartFile avatar,@RequestParam 
            String userName, @RequestParam String email,
             RedirectAttributes attributes) throws Exception {
        try {
            userService.modify(avatar,userName, email);
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        
        return new RedirectView("/");
        
    }
    @GetMapping("/record/{role}")
    public ModelAndView createUser(@PathVariable String role) {
        ModelAndView mav = new ModelAndView("user");
        UserEntity user = new UserEntity();
        user.setRole(RoleEnum.valueOf(role.toUpperCase()));
        mav.addObject("object", user);
        mav.addObject("title", "Sign up");
        mav.addObject("action", "save");
        return mav;
    }

    @GetMapping("/panel")
    public ModelAndView panel() {
        ModelAndView mav = new ModelAndView("panelEntry");
        mav.addObject("titlePage", "panel");
        mav.addObject("title", "Register of user");
        mav.addObject("action", "save");
        return mav;
    }
    @GetMapping("/editPassword")
    public ModelAndView modifyPassword() {
        ModelAndView mav = new ModelAndView("modifyPass");
        mav.addObject("object", userService.session());
        mav.addObject("title", "Modify password");
        mav.addObject("action", "save");
        return mav;
    }
    @PostMapping("/saveNewPassword")
    public RedirectView savingChanges(@RequestParam 
            String pass,
             RedirectAttributes attributes) throws Exception {
        try {
            userService.modifyPassword(pass);
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        
        return new RedirectView("/");
        
    }
    
    @GetMapping("/edit")
    public ModelAndView modifyUser() {
        ModelAndView mav = new ModelAndView("userModify");
        mav.addObject("object", userService.session());
        mav.addObject("title", "Update the user");
        mav.addObject("action", "save");
        return mav;
    }

    @GetMapping("/personalData")
    public ModelAndView showUserDetails() {
        ModelAndView mav = new ModelAndView("personalData");
        mav.addObject("guest", guestService.currentGuest());
        mav.addObject("host", hostService.currentHost());
        mav.addObject("user", userService.session());
        mav.addObject("titleUser", "User data");
        mav.addObject("titleHost", "Host data");
        mav.addObject("titleGuest", "Guest data");
        mav.addObject("title", "Personal data");
        mav.addObject("btMsg", "Book");
        return mav;
    }
    
}
