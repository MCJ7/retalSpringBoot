
package com.mcj.rent.services;

import com.mcj.rent.entities.HouseEntity;
import com.mcj.rent.entities.PicEntity;
import com.mcj.rent.enums.TypePropertyEnum;
import com.mcj.rent.repositories.HouseRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author maxco
 */
@Service
public class HouseService {

    @Autowired
    private HouseRepository repository;
    @Autowired
    private HostService hostService;
    @Autowired
    private PicService picService;

    /**
     *
     * @param file
     * @param street
     * @param number
     * @param zipCode
     * @param country
     * @param sinceDate
     * @param toDate
     * @param numberOfRooms
     * @param numberOfBaths
     * @param timeMin
     * @param timeMax
     * @param price
     * @param typeHouse
     * @param city
     * @throws Exception
     */
    public void create(MultipartFile file,String street, Integer number, 
            String zipCode, String country, LocalDate sinceDate,
            LocalDate toDate,Integer numberOfRooms,Integer numberOfBaths, 
            Integer timeMin, Integer timeMax, Double price, String typeHouse,
            String city) throws Exception {

        HouseEntity entity = new HouseEntity();
        entity.setStreet(street);
        entity.setNumber(number);
        entity.setZipCode(zipCode);
        entity.setCountry(country);
        entity.setCity(city);
        entity.setNumberOfRooms(numberOfRooms);
        entity.setNumberOfBaths(numberOfBaths);
        entity.setSinceDate(sinceDate);
        entity.setToDate(toDate);
        entity.setTimeMin(timeMin);
        entity.setTimeMax(timeMax);
        entity.setPrice(price);
        entity.setTypeHouse(TypePropertyEnum.valueOf(typeHouse));
        entity.setEnable(true);
        PicEntity pic = picService.save(file);
        entity.setPic(pic);
        repository.save(entity);
        hostService.assignHouse(entity);

    }

    /**
     *
     * @param file
     * @param id
     * @param street
     * @param number
     * @param zipCode
     * @param country
     * @param sinceDate
     * @param toDate
     * @param numberOfRooms
     * @param numberOfBaths
     * @param timeMin
     * @param timeMax
     * @param price
     * @param typeHouse
     * @param city
     * @throws Exception
     */
    @Transactional
    public void modify(MultipartFile file,String id, String street,
            Integer number, String zipCode, String country, LocalDate sinceDate,
            LocalDate toDate,Integer numberOfRooms,Integer numberOfBaths, 
            Integer timeMin, Integer timeMax, double price, String typeHouse,
             String city) throws Exception {
        HouseEntity entity = repository.getById(id);
        entity.setStreet(street);
        entity.setNumber(number);
        entity.setZipCode(zipCode);
        entity.setCountry(country);
        entity.setCity(city);
        entity.setNumberOfRooms(numberOfRooms);
        entity.setNumberOfBaths(numberOfBaths);
        entity.setSinceDate(sinceDate);
        entity.setToDate(toDate);
        entity.setTimeMin(timeMin);
        entity.setTimeMax(timeMax);
        entity.setPrice(price);
        entity.setTypeHouse(TypePropertyEnum.valueOf(typeHouse));
        entity.setEnable(true);
        String picId = null;
        if(entity.getPic() !=null){
            picId = entity.getPic().getId();
        }
        PicEntity pic = picService.modify(picId, file);
        entity.setPic(pic);
        repository.save(entity);

    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<HouseEntity> list() {
        return repository.findAll();
    }

    /**
     *
     * @param id
     */
    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public HouseEntity searchId(String id) {
        Optional<HouseEntity> entity = repository.findById(id);
        return entity.orElse(null);
    }

    /**
     *
     * @param id
     */
    @Transactional
    public void enable(String id) {
        HouseEntity f = repository.findById(id).get();
        if (f.getEnable()) {
            f.setEnable(Boolean.FALSE);
        } else {
            f.setEnable(Boolean.TRUE);
        }
    }

}
