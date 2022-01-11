
package com.mcj.rent.services;

import com.mcj.rent.entities.PicEntity;
import com.mcj.rent.exception.ValidationException;
import com.mcj.rent.repositories.PicRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author maxco
 */
@Service
public class PicService {


    @Autowired
    public PicRepository repository;

    /**
     *
     * @param file
     * @return
     * @throws ValidationException
     */
    public PicEntity save(MultipartFile file) throws ValidationException {

        try {
            PicEntity pic = new PicEntity();
            pic.setMime(file.getContentType());
            pic.setName(file.getName());
            pic.setConten(file.getBytes());
            return repository.save(pic);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    /**
     *
     * @param id
     * @param file
     * @return
     * @throws ValidationException
     */
    public PicEntity modify(String id,MultipartFile file) throws ValidationException {

        try {
            PicEntity pic = new PicEntity();
            if(id != null){
                Optional<PicEntity> respose= repository.findById(id);
                if(respose.isPresent()){
                    pic = respose.get();
                    System.out.println("");
                }
            }
            pic.setMime(file.getContentType());
            pic.setName(file.getName());
            pic.setConten(file.getBytes());
            return repository.save(pic);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}
