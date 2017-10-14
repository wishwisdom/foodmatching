package com.foodmatching.database;

import com.foodmatching.domain.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2017. 9. 15..
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
