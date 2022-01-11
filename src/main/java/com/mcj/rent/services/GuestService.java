
package com.mcj.rent.services;

import com.mcj.rent.entities.GuestEntity;
import com.mcj.rent.entities.UserEntity;
import com.mcj.rent.exception.ValidationException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mcj.rent.repositories.GuestRepository;


/**
 *
 * @author maxco
 */
@Service
public class GuestService {

    @Autowired
    private GuestRepository repository;
    @Autowired
    private UserService userService;

    /**
     *
     * @param fullName
     * @param street
     * @param number
     * @param zipCode
     * @param city
     * @param country
     * @throws Exception
     */
    public void create(String fullName, String street, Integer number, 
            String zipCode, String city, String country) throws Exception {
        validation(fullName,street,number,zipCode,city,country);
        GuestEntity guestEntity = new GuestEntity();
        guestEntity.setFullName(fullName);
        guestEntity.setStreet(street);
        guestEntity.setNumber(number);
        guestEntity.setZipCode(zipCode);
        guestEntity.setCity(city);
        guestEntity.setEnable(Boolean.TRUE);
        guestEntity.setCountry(country);
        guestEntity.setUser(userService.lastUser());
        repository.save(guestEntity);
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
     * @throws Exception
     */
    @Transactional
    public void modify(String id, String fullName, String street, 
            Integer number, String zipCode, String city, String country) throws Exception {
        validation(fullName,street,number,zipCode,city,country);
        GuestEntity guestEntity = repository.getById(id);
        guestEntity.setFullName(fullName);
        guestEntity.setStreet(street);
        guestEntity.setNumber(number);
        guestEntity.setZipCode(zipCode);
        guestEntity.setCity(city);
        guestEntity.setCountry(country);
        repository.save(guestEntity);

    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<GuestEntity> list() {
        return repository.findAll();
    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<GuestEntity> currentGuest() {
        return repository.findByUser(userService.session());
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
    @Transactional(readOnly = true)
    public GuestEntity searchId(String id) {
        Optional<GuestEntity> rentEntity = repository.findById(id);
        return rentEntity.orElse(null);
    }

    /**
     *
     * @param user
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<GuestEntity> searchGuest(UserEntity user) {
        return  repository.findByUser(user);
    }

    /**
     *
     * @param id
     */
    @Transactional
    public void enable(String id) {
        GuestEntity c = repository.findById(id).get();
        if (c.getEnable()) {
            c.setEnable(Boolean.FALSE);
        } else {
            c.setEnable(Boolean.TRUE);
        }
        repository.save(c);
    }

    /**
     *
     * @param name
     * @param street
     * @param number
     * @param zipCode
     * @param city
     * @param country
     * @throws ValidationException
     */
    public void validation(String name, String street, Integer number,
            String zipCode, String city, String country) throws ValidationException {
        if (name.trim().isEmpty()) {
            throw new ValidationException("Did not detect any name");
        }
        if (street.trim().isEmpty()) {
            throw new ValidationException("Did not detect any street");
        }
        if (zipCode.trim().isEmpty()) {
            throw new ValidationException("Did not detect any zipcode");
        }
        if (city.trim().isEmpty()) {
            throw new ValidationException("Did not detect any city");
        }
        if (country.trim().isEmpty()) {
            throw new ValidationException("Did not detect any country");
        }
        if (number<0) {
            throw new ValidationException("The number is least than 0");
        }
    }
}
