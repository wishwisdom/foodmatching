package com.foodmatching.database;

import com.foodmatching.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2017. 9. 13..
 */
public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);
}
