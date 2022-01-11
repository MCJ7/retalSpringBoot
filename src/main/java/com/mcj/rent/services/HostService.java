
package com.mcj.rent.services;

import com.mcj.rent.entities.HostEntity;
import com.mcj.rent.entities.HouseEntity;
import com.mcj.rent.entities.RentalEntity;
import com.mcj.rent.entities.UserEntity;
import com.mcj.rent.exception.ValidationException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mcj.rent.repositories.HostRepository;
import java.time.LocalDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author maxco
 */
@Service
public class HostService {
    @Autowired
    private HostRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private HostService hostService;
    
    /**
     *
     * @param fullName
     * @param personalIdentityCode
     * @param city
     * @param country
     * @throws Exception
     */
    public void create(String fullName,String personalIdentityCode, String city, String country) throws Exception {

        HostEntity hostEntity = new HostEntity();
        validation(fullName, personalIdentityCode, city,country);
        hostEntity.setFullName(fullName);
        hostEntity.setCountry(country);
        hostEntity.setPersonalIdentityCode(personalIdentityCode);;
        hostEntity.setCity(city);
        hostEntity.setAccountBalance(0.0);
        hostEntity.setUser(userService.lastUser());
        hostEntity.setEnable(Boolean.TRUE);
        repository.save(hostEntity);
    }

    /**
     *
     * @param id
     * @param fullName
     * @param personalIdentityCode
     * @param city
     * @param country
     * @throws Exception
     */
    @Transactional
    public void modify(String id,String fullName,String personalIdentityCode,String city, String country) throws Exception {
        HostEntity hostEntity = repository.getById(id);
        hostEntity.setFullName(fullName);
        hostEntity.setCountry(country);
        hostEntity.setPersonalIdentityCode(personalIdentityCode);
        hostEntity.setCity(city);
        repository.save(hostEntity);

    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<HostEntity> list() {
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
     * @param house
     */
    @Transactional
    public void assignHouse(HouseEntity house) {
        Authentication authentication = SecurityContextHolder.
        getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserEntity user = userService.findByUserName(currentUser);
        HostEntity host = hostService.searchUserId(user);
        host.setHouse(house);
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public HostEntity searchId(String id) {
        Optional<HostEntity> entity = repository.findById(id);
        return entity.orElse(null);
    }

    /**
     *
     * @param user
     * @return
     */
    @Transactional(readOnly = true)
    public HostEntity searchUserId(UserEntity user) {
        Optional<HostEntity> entity = repository.findByUser(user);
        return entity.get();
    }

    /**
     *
     * @param id
     */
    @Transactional
    public void enable(String id) {

        HostEntity hostEntity = repository.findById(id).get();
        if (hostEntity.getEnable()) {
            hostEntity.setEnable(Boolean.FALSE);
        }else{
            hostEntity.setEnable(Boolean.TRUE);
        }
    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<HostEntity> currentHost() {
        return repository.findByUser(userService.session());
    }

    /**
     *
     * @param rental
     */
    @Transactional
    public void incrementBalance(RentalEntity rental) {
        HostEntity hostEntity = repository.findByHouse(rental.getHouse()).get();
        System.out.println(hostEntity.toString());
        Double price = rental.getHouse().getPrice()*(rental.getCheckOut().compareTo(rental.getCheckIn()));
        hostEntity.setAccountBalance(hostEntity.getAccountBalance()+price);
        System.out.println("Price:                     "+price);
        repository.save(hostEntity);
    }

    /**
     *
     * @param name
     * @param street
     * @param city
     * @param country
     * @throws ValidationException
     */
    public void validation(String name, String street, String city, String country) throws ValidationException {
        if (name.trim().isEmpty()) {
            throw new ValidationException("Did not detect any name");
        }
        if (street.trim().isEmpty()) {
            throw new ValidationException("Did not detect any street");
        }
        if (city.trim().isEmpty()) {
            throw new ValidationException("Did not detect any city");
        }
        if (country.trim().isEmpty()) {
            throw new ValidationException("Did not detect any country");
        }
    }
    
}
