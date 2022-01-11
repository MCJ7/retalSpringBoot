
package com.mcj.rent.repositories;

import com.mcj.rent.entities.*;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author maxco
 */
@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, String> {

    Optional<GuestEntity> findByUserId(String userId);
    Optional<GuestEntity> findByUser(UserEntity user);
}
