package com.foodmatching.presentation.controller;

import com.foodmatching.database.BoardRepository;
import com.foodmatching.database.ScrapRepository;
import com.foodmatching.domain.model.CustomUser;
import com.foodmatching.domain.model.Scrap;
import com.foodmatching.domain.model.board.Board;
import com.foodmatching.domain.model.user.User;
import com.foodmatching.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by shin on 2017. 11. 28..
 */
@RestController
public class ScrapController {

    @Autowired
    private UserService userService;
    @Autowired
    private ScrapRepository scrapRepository;

    @GetMapping("/scraps")
    public ResponseEntity myScrap(CustomUser customUser) {
        User user = userService.findBy(customUser.getUserEmail());


        // TODO update scrap list
//		List<Scrap> list = scrapMapper.findAll(scrap, start, end);
//
//		model.addAttribute("scrapList", list);
//		model.addAttribute("start",start);
//		model.addAttribute("end",end);

        List<Scrap> scraps = user.getPocket().getScraps();

        return ResponseEntity.ok(scraps);
    }

    @PostMapping("/scraps")
    public ResponseEntity create(@ModelAttribute("customUser") CustomUser user, Long boardId){

        userService.saveScrap(user.getUser(),boardId);

        return ResponseEntity.ok().build();
    }

    /**
     * Save 'scrap' if not exists in a table or delete it.
     *
     * @param id   board id for user's scrap or unscrap
     * @param user login user
     * @return total like number of the board {id}
     */
    @PostMapping(value = "/scrap/{id}")
    @ResponseBody
    public ResponseEntity updateScrap(@PathVariable("id") Long id, @ModelAttribute("customUser") CustomUser user) {

        Scrap s = scrapRepository.findOne(id);

        if(s == null){
            throw new RuntimeException("Can't find scrap "+id);
        }
        // TODO 실제로 지워지는 확인필요. 실제 user를 찾고 지원볼 예정.
        user.getUser().getPocket().remove(s);


       return ResponseEntity.ok().build();
    }
}
