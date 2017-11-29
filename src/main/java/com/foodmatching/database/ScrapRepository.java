package com.foodmatching.database;

import com.foodmatching.domain.model.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2017. 11. 29..
 */
public interface ScrapRepository extends JpaRepository<Scrap,Long> {
}
