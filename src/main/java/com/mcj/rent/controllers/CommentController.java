
package com.mcj.rent.controllers;

import com.mcj.rent.entities.CommentEntity;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import com.mcj.rent.services.CommentService;
import com.mcj.rent.services.HouseService;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author maxco
 */
@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService service;
    @Autowired
    private HouseService houseService;

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView listComment(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("comment");
        mav.addObject("title", "List of comments");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }

        mav.addObject("objects", service.list());
        mav.addObject("title", "List of comment");
        mav.addObject("btCreate", "Record comment");
        mav.addObject("emptyList", "Did not match any comment");
        return mav;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editComment(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("commentForm");
        mav.addObject("object", service.searchId(id));
        mav.addObject("title", "Edit comment");
        mav.addObject("action", "update");
        return mav;
    }

    /**
     *
     * @param house
     * @return
     */
    @GetMapping("/create/{house}")
    public ModelAndView createComment(@PathVariable String house) {
        ModelAndView mav = new ModelAndView("commentForm");
        CommentEntity comment = new CommentEntity() ;
        comment.setHouse(houseService.searchId(house));
        mav.addObject("object",comment );
        mav.addObject("title", "Comment Registration Form");
        mav.addObject("action", "save");
        return mav;
    }

    /**
     *
     * @param description
     * @param house
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public RedirectView saveComment(@RequestParam String description,
            @RequestParam String house,
            RedirectAttributes attributes) throws Exception {
        try {

            service.create(description,house);
            attributes.addFlashAttribute("exito-name", "Successful save");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }

        return new RedirectView("/");
    }

    /**
     *
     * @param id
     * @param description
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modifComment(@RequestParam String id, 
            @RequestParam String description, RedirectAttributes attributes) 
            throws Exception {
        try {
            service.modify(id, description);
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/comments");
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView deleteComment(@PathVariable String id, 
            RedirectAttributes attributes) {
        try {
            service.delete(id);
            attributes.addFlashAttribute("exito-name", "Successful delete");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/comments");
    }
}
