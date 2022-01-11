
package com.mcj.rent.repositories;

import com.mcj.rent.entities.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author maxco
 */
@Repository
public interface HouseRepository extends JpaRepository<HouseEntity, String>{

}
