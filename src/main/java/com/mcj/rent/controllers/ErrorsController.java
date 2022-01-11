
package com.mcj.rent.controllers;

import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author maxco
 */
@Controller
public class ErrorsController implements ErrorController{
    
    @RequestMapping(value = "/error", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView showError(HttpServletResponse sr){
        ModelAndView mav = new ModelAndView("error");
        String message;
        String title = "Error "+sr.getStatus();
        mav.addObject("code",sr.getStatus());
        mav.addObject("title",title);
        switch(sr.getStatus()){
            case 500:
                message ="Cannot establish the comunication:internal server";
                break;
            case 404:
                message ="Page not found";
                break;
            case 403:
                message = "Did not have permission";
                break;
            default:
                message = "Unexpected error";
        }
        mav.addObject("message", message);
        
        return mav;
    }
}
