
package com.mcj.rent.repositories;

import com.mcj.rent.entities.GuestEntity;
import com.mcj.rent.entities.RentalEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author maxco
 */
@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, String>{
    List<RentalEntity> findByGuest(GuestEntity guest);
}
