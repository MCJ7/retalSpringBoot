
package com.mcj.rent.services;

import com.mcj.rent.entities.*;
import com.mcj.rent.exception.ValidationException;
import com.mcj.rent.repositories.HouseRepository;
import java.time.LocalDate;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.mcj.rent.repositories.RentalRepository;

/**
 *
 * @author maxco
 */
@Service
public class RentalService {

    @Autowired
    private RentalRepository repository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private GuestService guestService;
    @Autowired
    private HostService hostService;
    @Autowired
    private UserService userService;
    
    /**
     *
     * @param guest
     * @param checkIn
     * @param checkOut
     * @param house
     * @param userName
     * @throws Exception
     */
    @Transactional
    public void create(String guest, LocalDate checkIn, LocalDate checkOut, String 
            house,String userName) throws Exception {
        if(houseRepository.findById(house).get().getSinceDate().isAfter(checkIn)){
            throw  new ValidationException("The date is not available");
        }
        if(houseRepository.findById(house).get().getToDate().isBefore(checkOut)){
            throw  new ValidationException("The date is not available");
        }
        if(checkIn.isAfter(checkOut)){
            throw  new ValidationException("The date is not wrong");
        }
        RentalEntity rentalEntity = new RentalEntity();
        rentalEntity.setBookingName(guest);
        rentalEntity.setCheckIn(checkIn);
        rentalEntity.setCheckOut(checkOut);
        rentalEntity.setEnable(Boolean.TRUE);
        rentalEntity.setHouse(houseRepository.findById(house).get());
        UserEntity user = userService.findByUserName(userName);
        rentalEntity.setGuest(guestService.searchGuest(user).get());
        repository.save(rentalEntity);
        hostService.incrementBalance(rentalEntity);
    }

    /**
     *
     * @param id
     * @param bookingName
     * @param checkIn
     * @param checkOut
     * @throws Exception
     */
    @Transactional
    public void modify(String id, String bookingName, 
            LocalDate checkIn, LocalDate checkOut) throws Exception {
        RentalEntity rentalEntity = repository.getById(id);
        rentalEntity.setBookingName(bookingName);
        rentalEntity.setCheckIn(checkIn);
        rentalEntity.setCheckOut(checkOut);
        repository.save(rentalEntity);

    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<RentalEntity> list() {
        return repository.findAll();
    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<RentalEntity> guestList(){
        return repository.findByGuest(guestService.currentGuest().get());
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
    public RentalEntity searchId(String id) {
        Optional<RentalEntity> rentEntity = repository.findById(id);
        return rentEntity.orElse(null);
    }

    /**
     *
     * @param id
     */
    @Transactional
    public void enable(String id) {
        RentalEntity rentalEntity = repository.findById(id).get();
        if (rentalEntity.getEnable()) {
            rentalEntity.setEnable(Boolean.FALSE);
        } else {
            rentalEntity.setEnable(Boolean.TRUE);
        }
    }
}
