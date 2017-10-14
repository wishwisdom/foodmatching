package com.foodmatching.domain.service;

import com.foodmatching.database.BoardRepository;
import com.foodmatching.database.FoodRepository;
import com.foodmatching.database.ReplyRepository;
import com.foodmatching.domain.model.Food;
import com.foodmatching.domain.model.Reply;
import com.foodmatching.domain.model.board.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by shin on 2017. 9. 13..
 */
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ReplyRepository replyRepository;
    public Board findBy(Long id){
        return boardRepository.findOne(id);
    }

    @Transactional
    public void save(Board board){
        boardRepository.save(board);
    }

    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    public long count(){
        return boardRepository.count();
    }

    public Reply save(Reply r){
        return replyRepository.save(r);
    }

    public Food findBy(Long foodId, String foodName){
        Food f = foodRepository.findOne(foodId);

        return f;
    }

}
