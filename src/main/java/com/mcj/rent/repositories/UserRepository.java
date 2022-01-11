
package com.mcj.rent.repositories;

import com.mcj.rent.entities.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author maxco
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
    
    Optional<UserEntity> findByUserName(String userName);
    public boolean existsUserByUserName(String userName);
    
}
