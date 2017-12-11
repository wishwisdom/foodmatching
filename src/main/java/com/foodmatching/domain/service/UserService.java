package com.foodmatching.domain.service;

import com.foodmatching.database.AuthorityRepository;
import com.foodmatching.database.BoardRepository;
import com.foodmatching.database.ScrapRepository;
import com.foodmatching.database.UserRepository;
import com.foodmatching.domain.model.CustomUser;
import com.foodmatching.domain.model.Scrap;
import com.foodmatching.domain.model.board.Board;
import com.foodmatching.domain.model.user.Authority;
import com.foodmatching.domain.model.user.User;
import com.foodmatching.domain.model.user.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by shin on 2017. 9. 13..
 */
@Slf4j
@Service
public class UserService implements UserDetailsService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    private ScrapRepository scrapRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void save(User user){
        userRepository.save(user);
        Authority authority = new Authority(user, UserRole.ROLE_USER);

        authorityRepository.save(authority);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if(user == null){
            throw new RuntimeException("존재하지 않는 id입니다");
        }

        CustomUser u = new CustomUser(user);

        return u;
    }

    public User findBy(String email){
        User user = userRepository.findByEmail(email);

//        if(user == null){
//            throw new RuntimeException("존재하지 않는 id입니다");
//        }

        return user;
    }

    @Transactional
    public void saveScrap(User u, Long boardId){
        User user = userRepository.findByEmail(u.getEmail());
        Board b = boardRepository.findOne(boardId);

        Scrap s = new Scrap(user, b);

        scrapRepository.save(s);

        user.addScrap(s);
    }
}
