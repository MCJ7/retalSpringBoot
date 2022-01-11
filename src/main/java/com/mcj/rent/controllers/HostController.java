
package com.mcj.rent.controllers;

import com.mcj.rent.entities.HostEntity;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import com.mcj.rent.services.HostService;

/**
 *
 * @author maxco
 */
@Controller
@RequestMapping("/hosts")
public class HostController {

    @Autowired
    private HostService service;

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("")
    public ModelAndView listHost(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("host");
        mav.addObject("title", "Host Registration Form");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }

        mav.addObject("objects", service.list());
        mav.addObject("title", "List of hosts");
        mav.addObject("btCreate", "Record host");
        mav.addObject("emptyList", "Did not match any hosts");
        return mav;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editHost(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("hostForm");
        mav.addObject("object", service.searchId(id));
        mav.addObject("title", "Update host");
        mav.addObject("action", "update");
        return mav;
    }

    /**
     *
     * @return
     */
    @GetMapping("/create")
    public ModelAndView createHost() {
        ModelAndView mav = new ModelAndView("hostForm");
        mav.addObject("object",new HostEntity());
        mav.addObject("title", "Record of host");
        mav.addObject("action", "save");
        return mav;
      
    }

    /**
     *
     * @param fullName
     * @param personalIdentityCode
     * @param city
     * @param country
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public RedirectView saveHost( @RequestParam String fullName,
            @RequestParam String personalIdentityCode,
            @RequestParam String city, @RequestParam String country,
            RedirectAttributes attributes) throws Exception {
        try {
            service.create(fullName, personalIdentityCode, city,country);
            attributes.addFlashAttribute("exito-name", "Successful save");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }

        return new RedirectView("/");
    }

    /**
     *
     * @param id
     * @param fullName
     * @param personalIdentityCode
     * @param city
     * @param country
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    public RedirectView modifyHost(@RequestParam String id, 
            @RequestParam String fullName,
            @RequestParam String personalIdentityCode,
            @RequestParam String city, @RequestParam String country,  
            RedirectAttributes attributes) throws Exception {
        try {
            service.modify(id,fullName, personalIdentityCode, city,country);
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
           
        }
        return new RedirectView("/hosts");
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/state/{id}")
    public RedirectView enableHost(@PathVariable String id, 
            RedirectAttributes attributes) {
        try {
            service.enable(id);
            attributes.addFlashAttribute("exito-name", "Successful state");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/hosts");
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/delete/{id}")
    public RedirectView deleteHost(@PathVariable String id, RedirectAttributes attributes) {
        try {
            service.delete(id);
            attributes.addFlashAttribute("exito-name", "Successful delete");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/hosts");
    }
}
