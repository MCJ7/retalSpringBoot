
package com.mcj.rent.repositories;

import com.mcj.rent.entities.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author maxco
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String>{
    List<CommentEntity> findByHouse(HouseEntity house);
    
}
