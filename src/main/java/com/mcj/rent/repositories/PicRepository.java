
package com.mcj.rent.repositories;

import com.mcj.rent.entities.PicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author maxco
 */
@Repository
public interface PicRepository  extends JpaRepository<PicEntity, String>{
    
}
