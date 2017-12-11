package com.foodmatching.presentation.controller;

import com.foodmatching.domain.model.CustomUser;
import com.foodmatching.domain.model.FileUploadForm;
import com.foodmatching.domain.model.Reply;
import com.foodmatching.domain.model.board.Board;
import com.foodmatching.domain.model.board.BoardDetail;
import com.foodmatching.domain.model.board.BoardForm;
import com.foodmatching.domain.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Controller
public class BoardController {
	private int pageOffset = 6;
	private String regex = ",";
	@Autowired
	private BoardService boardService;

	/**
	 * Returns a view page title which let a client be able to write a board.
	 * Must be a user authorized.
	 *
	 * @param model
	 *            Can bring a or parameters to obejects for showing them in view
	 *            page
	 * @return string Return a view page title excluding '.html'.
	 */
	@GetMapping("/matches/upload")
	public String uploadFile(Model model) {

		FileUploadForm fuf = new FileUploadForm();

		model.addAttribute("fileUpload", fuf);

		return "food/write_food";
	}

	/**
	 * Create a board including food pictures and tags...
	 *
	 * @param user
	 *            a current authorized User
	 * @return redirect a detailed board view
	 *             IOException
	 * @see BoardDetail
	 */
	@PostMapping("/matches/upload")
	public @ResponseBody String saveFile( BoardForm bf,
			 CustomUser user) {

		log.info("multipart : {}",bf);



		return "redirect:/matches/1"; //+ b.getId();
	}

	
	/**
	 * Return thumbnail list.
	 *
	 * @param model
	 *            including food names, food pictures and tags.
	 * @param startNum
	 *            a current authorized User
	 * @param offset
	 *            Will show board numbers
	 * @return List<Board> Return board list by json form
	 */
	@GetMapping("/matches")
	public String matchList(Model model,
			@RequestParam(value = "startNum", required = false, defaultValue = "0") Integer startNum,
			@RequestParam(value = "offset", required = false, defaultValue = "3") int offset) {
		log.info("startNum :" + startNum);
		log.info("offset :" + offset);

		Integer start = startNum;
		offset = offset == 0 ? this.pageOffset : offset;

		// board의 total number를 확
		long total = boardService.count();

		/*
		 * 서버에 있는 데이터에 따라 쿼리를 다르게 한다.
		 */

		if (total > start) {
			// model.addAttribute("boardlist", boardList);
			log.info("StartNum : " + startNum);

			List<Board> boards = boardService.findAll();
			model.addAttribute("startNum", startNum + offset);
			model.addAttribute("boards", boards);
		}

		return "food/food_content";
	}

	/**
	 * Return {id} detail board view
	 * 
	 * @param model
	 *            Including board info such as board, food images and replies
	 * @param id
	 *            a board id
	 * @return String Return a board detail view
	 */
	@GetMapping("/matches/{id}")
	public String matchDetail(Model model, @PathVariable Long id, Principal principal) {

		Board bd = boardService.findBy(id);
		model.addAttribute("bd", bd);

		//logger.info("reply :" + bd.getCommentList().size());
		// 리플 틀 부
		model.addAttribute("reply", bd.getReplies());
		// 다린 리플 추가 예
		log.info("User Name : " + principal.getName());
//		Like like = new Like(id,)
//		Like isLike = likeMapper.find(like);
//
//		logger.info("like test :" + (isLike == null));
//
//		if (isLike == null) {
//			likeMapper.save(like);
//		} else
//			likeMapper.delete(like);

		return "matches/match_info";
	}

	/**
	 * Update {id} board info and return the updated board detail view.
	 * 
	 * @param id
	 *            updating board id
	 * @param fuf
	 *            An object updating a board content, food name or food images
	 * @return redirect the view by updated {id} board
	 */
	@PostMapping("/matches/{id}/update")
	public String updateMatch(@PathVariable String id, FileUploadForm fuf) {
		return "redirect:/matches/" + id;
	}

	@RequestMapping(value = "/matches/{id}/reply", method = RequestMethod.POST)
	public String saveReply(@PathVariable("id") Long boardId, @RequestParam("comment") String comment,
			@ModelAttribute("customUser") CustomUser user, Model model) {
		log.info("comment:" + comment);
		Board b = boardService.findBy(boardId);
		Reply r = new Reply(b,comment,user.getUser());
		r = boardService.save(r);

		model.addAttribute("comment", r);

		return "comment";
	}

	@GetMapping("/matches/reply/{id}")
	public void updateReply(@PathVariable("id") int replyId, @RequestParam("comment") String comment,
			@ModelAttribute("customUser") CustomUser user, Model model) {
		log.info("updat comment : " + comment);

	}

	/**
	 * Save 'like' if not exists in a table or delete it.
	 * 
	 * @param id
	 *            board id for user's like or unlike
	 * @param user
	 *            login user
	 * 
	 * @return total like number of the board {id}
	 */
//	@GetMapping(value = "/matches/like/{id}")
//	@ResponseBody
//	public Map<String,Object> updateLike(@PathVariable("id") Integer id, @ModelAttribute("customUser") CustomUser user) {
//		Map<String, Object> likeInfo = new HashMap<String, Object>();
//
//		Like like = new Like(id, user.getUserEmail());
//
//		Like isLike = likeMapper.find(like);
//
//		logger.info("like test :" + (isLike == null));
//		boolean statusLike = false;
//		if (isLike == null) {
//			likeMapper.save(like);
//			statusLike = true;
//		} else
//			likeMapper.delete(like);
//
//		likeInfo.put("count", likeMapper.countAll(id));
//		likeInfo.put("isLike", statusLike);
//
//		return likeInfo;
//	}



}
