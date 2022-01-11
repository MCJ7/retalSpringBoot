
package com.mcj.rent.services;

import com.mcj.rent.entities.*;
import com.mcj.rent.repositories.CommentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author maxco
 */
@Service
public class CommentService {

    @Autowired(required = true)
    private CommentRepository repository;
    @Autowired
    private HouseService houseService;
    @Autowired
    private GuestService guestService;

    /**
     *
     * @param description
     * @param house
     * @throws Exception
     */
    @Transactional
    public void create(String description, String house) throws Exception {

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setDescription(description);
        commentEntity.setHouse(houseService.searchId(house));

        System.out.println(guestService.currentGuest());
        commentEntity.setGuestEntity(guestService.searchId(guestService.currentGuest().get().getId()));
        repository.save(commentEntity);
    }

    /**
     *
     * @param id
     * @param description
     * @throws Exception
     */
    @Transactional
    public void modify(String id, String description) throws Exception {
        CommentEntity commentEntity = repository.getById(id);
        commentEntity.setDescription(description);
        repository.save(commentEntity);

    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<CommentEntity> list() {
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
     * @return
     */
    @Transactional
    public List<CommentEntity> findByHouse(HouseEntity house) {
        return repository.findByHouse(house);
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public CommentEntity searchId(String id) {
        Optional<CommentEntity> commentEntity = repository.findById(id);
        return commentEntity.orElse(null);
    }
}
