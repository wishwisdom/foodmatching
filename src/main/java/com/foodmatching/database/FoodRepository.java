package com.foodmatching.database;

import com.foodmatching.domain.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2017. 9. 16..
 */
public interface FoodRepository extends JpaRepository<Food,Long> {
}
