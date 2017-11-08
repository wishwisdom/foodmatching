package com.foodmatching.database;

import com.foodmatching.domain.model.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2017. 11. 5..
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
