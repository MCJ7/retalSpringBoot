
package com.mcj.rent.controllers;

import com.mcj.rent.entities.*;
import com.mcj.rent.exception.ServiceError;
import com.mcj.rent.services.*;
import com.mcj.rent.services.UserService;
import java.util.logging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author maxco
 */
@Controller
@RequestMapping("/pics")
public class PicController {
    @Autowired
    private HouseService houseService;
    @Autowired
    private UserService userService;
    
    @GetMapping("/house")
    public ResponseEntity<byte[]> getHousePic(@RequestParam String id) throws ServiceError{
        try {
            HouseEntity house = houseService.searchId(id);
            if(house.getPic() == null){
                  throw new ServiceError("The house has not pic.");
            }
            byte[] pic = house.getPic().getConten();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return  new ResponseEntity<>(pic,headers, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(PicController.class.getName()).log(Level.SEVERE,null,e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
    }
        @GetMapping("/user")
    public ResponseEntity<byte[]> getUserPic() throws ServiceError{
        try {
            UserEntity user = userService.session();
            if(user.getAvatar()== null){
                   throw new ServiceError("No avatar");
            }
            byte[] pic = user.getAvatar().getConten();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return  new ResponseEntity<>(pic,headers, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(PicController.class.getName()).log(Level.SEVERE,null,e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
    }
    
}
