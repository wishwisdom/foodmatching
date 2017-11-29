package com.foodmatching.presentation.controller;

import com.foodmatching.domain.model.CustomUser;
import com.foodmatching.domain.model.Scrap;
import com.foodmatching.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by shin on 2017. 11. 28..
 */
@RestController
public class ScrapController {

    @Autowired
    private UserService userService;

    @GetMapping("/scraps")
    public ResponseEntity myScrap(CustomUser user) {


        // TODO update scrap list
//		List<Scrap> list = scrapMapper.findAll(scrap, start, end);
//
//		model.addAttribute("scrapList", list);
//		model.addAttribute("start",start);
//		model.addAttribute("end",end);

        List<Scrap> scraps = user.getUser().getPocket().getScraps();

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
    public int updateScrap(@PathVariable("id") Long id, @ModelAttribute("customUser") CustomUser user) {

        //Scrap scrap = new Scrap(id, user.getUserEmail());
        // TODO scrap update like
//		Scrap isScrap = scrapMapper.find(scrap);
//
//		logger.info("scrap test :" + (isScrap==null));
//
//		if(isScrap == null){
//			scrapMapper.save(scrap);
//		}else
//			scrapMapper.delete(scrap);
//
//		return scrapMapper.countAll(scrap);
        // TODO scrap count 정보를 반환하는 것을 만들어줄 것
        return 1;
    }
}
