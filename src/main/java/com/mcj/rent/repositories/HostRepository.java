
package com.mcj.rent.repositories;

import com.mcj.rent.entities.HostEntity;
import com.mcj.rent.entities.HouseEntity;
import com.mcj.rent.entities.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author maxco
 */
@Repository
public interface HostRepository extends JpaRepository<HostEntity, String>{
    Optional<HostEntity> findByUser(UserEntity user);
    Optional<HostEntity> findByHouse(HouseEntity house);
}
