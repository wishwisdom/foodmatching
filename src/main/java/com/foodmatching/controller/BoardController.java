package com.foodmatching.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodmatching.mapper.FoodMapper;
import com.foodmatching.mapper.LikeMapper;
import com.foodmatching.model.Board;
import com.foodmatching.model.BoardDetail;
import com.foodmatching.model.BoardForm;
import com.foodmatching.model.CustomUser;
import com.foodmatching.model.FileUploadForm;
import com.foodmatching.model.Like;
import com.foodmatching.model.Reply;
import com.foodmatching.model.ThumbNail;
import com.foodmatching.serviceimpl.BoardServiceImpl;
import com.foodmatching.utils.FileUtil;

@Controller
public class BoardController {
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private int pageOffset = 6;
	private String regex = ",";
	@Autowired
	private BoardServiceImpl boardService;

	@Autowired
	private LikeMapper likeMapper;
	@Autowired
	private FoodMapper foodMapper;

	
	@Value("${board.img.path}")
	private String SAVE_PATH;

	/**
	 * Returns a view page title which let a client be able to write a board.
	 * Must be a user authorized.
	 *
	 * @param model
	 *            Can bring a or parameters to obejects for showing them in view
	 *            page
	 * @return string Return a view page title excluding '.html'.
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN') AND hasAuthority('ROLE_USER')")
	@GetMapping("/matches/upload")
	public String uploadFile(Model model) {

		FileUploadForm fuf = new FileUploadForm();

		model.addAttribute("fileUpload", fuf);
		model.addAttribute("ok", "Yet");

		return "foods/write_food";
	}

	/**
	 * Create a board including food pictures and tags...
	 *
	 * @param fuf
	 *            including food names, food pictures and tags.
	 * @param user
	 *            a current authorized User
	 * @return redirect a detailed board view
	 * @throws IllegaStateExecption,
	 *             IOException
	 * @see BoardDetail
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN') AND hasAuthority('ROLE_USER')")
	@PostMapping("/matches/upload")
	public @ResponseBody String saveFile( HttpServletRequest req,MultipartHttpServletRequest msr,
			@ModelAttribute("customUser") CustomUser user, Model model) {
		
		logger.info("multipartfile size : {}",req.getParameter("foodpic1name"));
		logger.info("multipartfile size : {}",req.getParameter("foodpic2name"));
		logger.info("file : {}",msr.getFiles("foodpic").size());
		logger.info("foodtaste1 :" +req.getParameter("foodtaste1"));
		logger.info("foodtaste2 :" +req.getParameter("foodtaste2"));
		logger.info("foodname1 : {}",req.getParameter("foodname1"));
		logger.info("foodname2 : {}",req.getParameter("foodname2"));
		logger.info("summary : {}",req.getParameter("summary"));
		logger.info("tag : {}",req.getParameter("tag"));
		
		BoardForm bf = new BoardForm();
		
		bf.setPictures(msr.getFiles("foodpic"));
		bf.setTastes1(Arrays.asList(req.getParameter("foodtaste1").split(regex)));
		bf.setTastes2(Arrays.asList(req.getParameter("foodtaste2").split(regex)));
		String[] foodNames = {req.getParameter("foodname1"),req.getParameter("foodname2")};
		List<String> foodImageNames = new ArrayList<>();
		foodImageNames.add(req.getParameter("foodpic1name"));
		if(req.getParameter("foodpic2name")!=null){
			foodImageNames.add(req.getParameter("foodpic2name"));
		}
		bf.setFoodNames(foodNames);
		bf.setFoodImageNames(foodImageNames);
		bf.setSummary(req.getParameter("summary"));
		bf.setTags(Arrays.asList(req.getParameter("tag").split(regex)));
		
		logger.info("toString : {}",bf);
		logger.info("food name : {}", msr.getFile("foodpic").getOriginalFilename());
		
		Board b = new Board();

		b.setOwner(user.getNickName());

		boardService.save(b, bf);

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
		logger.info("startNum :" + startNum);
		logger.info("offset :" + offset);

		Integer start = startNum;
		offset = offset == 0 ? this.pageOffset : offset;

		// board의 total number를 확
		int total = boardService.countTotalNum();

		/*
		 * 서버에 있는 데이터에 따라 쿼리를 다르게 한다.
		 */

		if (total > start) {
			// model.addAttribute("boardlist", boardList);
			logger.info("StartNum : " + startNum);
			List<ThumbNail> thumbNailList = boardService.findAll(start, offset);
			model.addAttribute("startNum", startNum + offset);
			model.addAttribute("thumbNailList", thumbNailList);
		}

		return "foods/food_content";
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
	public String matchDetail(Model model, @PathVariable int id, Principal principal) {

		BoardDetail bd = boardService.findById(id);
		model.addAttribute("bd", bd);

		logger.info("reply :" + bd.getCommentList().size());
		// 리플 틀 부
		model.addAttribute("reply", new Reply());
		// 다린 리플 추가 예
		logger.info("User Name : " + principal.getName());
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
	public String saveReply(@PathVariable("id") int boardId, @RequestParam("comment") String comment,
			@ModelAttribute("customUser") CustomUser user, Model model) {
		logger.info("comment:" + comment);
		Reply r = new Reply();
		r.setComment(comment);
		r.setBoardId(boardId);
		r.setReplyer(user.getNickName());
		boardService.saveReply(r);

		model.addAttribute("comment", r);

		return "comment";
	}

	@GetMapping("/matches/reply/{id}")
	public void updateReply(@PathVariable("id") int replyId, @RequestParam("comment") String comment,
			@ModelAttribute("customUser") CustomUser user, Model model) {
		logger.info("updat comment : " + comment);

	}

	/**
	 * Save 'like' if not exists in a table or delete it.
	 * 
	 * @param id
	 *            board id for user's like or unlike
	 * @param currentUser
	 *            login user
	 * 
	 * @return total like number of the board {id}
	 */
	@GetMapping(value = "/matches/like/{id}")
	@ResponseBody
	public Map<String,Object> updateLike(@PathVariable("id") Integer id, @ModelAttribute("customUser") CustomUser user) {
		Map<String, Object> likeInfo = new HashMap<String, Object>();

		Like like = new Like(id, user.getUserEmail());

		Like isLike = likeMapper.find(like);

		logger.info("like test :" + (isLike == null));
		boolean statusLike = false;
		if (isLike == null) {
			likeMapper.save(like);
			statusLike = true;
		} else
			likeMapper.delete(like);
		
		likeInfo.put("count", likeMapper.countAll(id));
		likeInfo.put("isLike", statusLike);
		
		return likeInfo;
	}

	
	
	
	
	/**
	 * Return a binary image file
	 * 
	 * @param fileName
	 *            a file name which a client requests
	 * @return Image The image file titled fileName
	 */
	@RequestMapping(value = "/img/{boardId}/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFile(@PathVariable("boardId") Integer boardId,@PathVariable("filename") String fileName) {
		logger.info("FILE ID: " + fileName);
		logger.info("SAVE_PATH : " + SAVE_PATH);
		logger.info("board id: {}",boardId);
		String foodImageName = foodMapper.findByFoodName(boardId,fileName);
		byte[] media = FileUtil.getFile(SAVE_PATH,foodImageName);
		HttpHeaders headers = new HttpHeaders();

		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
		return responseEntity;

	}

}
