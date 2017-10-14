package com.foodmatching.database;

import com.foodmatching.domain.model.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2017. 9. 10..
 */
public interface BoardRepository extends JpaRepository<Board,Long> {
}
