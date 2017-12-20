package com.foodmatching.domain.service;

import com.foodmatching.database.BoardRepository;
import com.foodmatching.database.FoodRepository;
import com.foodmatching.database.ReplyRepository;
import com.foodmatching.database.UserRepository;
import com.foodmatching.domain.model.Food;
import com.foodmatching.domain.model.Reply;
import com.foodmatching.domain.model.board.Board;
import com.foodmatching.domain.model.board.BoardForm;
import com.foodmatching.domain.model.user.User;
import com.foodmatching.utils.AWSUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by shin on 2017. 9. 13..
 */
@Service
public class BoardService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ReplyRepository replyRepository;


    @Autowired
    private AWSUploader uploader;


    public Board findBy(Long id){
        return boardRepository.findOne(id);
    }

    @Transactional
    public void create(BoardForm form, Long userId){

        User user = userRepository.findOne(userId);

        Board b = new Board();
        b.setSummary(form.getSummary());
        b.setLikes(0);
        b.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        b.setOwner(user);


        boardRepository.save(b);





    }

    @Transactional
    public void save(Board board){
        boardRepository.save(board);
    }

    public Page<Board> findAll(Pageable pageable){
        return boardRepository.findAll(pageable);
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
