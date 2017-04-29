package com.foodmatching.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foodmatching.mapper.LikeMapper;
import com.foodmatching.model.Board;
import com.foodmatching.model.BoardDetail;
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
	@Autowired
	private BoardServiceImpl boardService;
	
	@Autowired
	private LikeMapper likeMapper;

	
	/**
	 * Returns a view page title which let a client be able to write a board.
	 * Must be a user authorized. 
	 *
	 * @param  model	Can bring a or parameters to obejects for showing them in view page 
	 * @return string  Return a view page title excluding '.html'.     
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN') AND hasAuthority('ROLE_USER')")
	@GetMapping("/matches/upload")
	public String uploadFile(Model model) {

		FileUploadForm fuf = new FileUploadForm();

		model.addAttribute("fileUpload", fuf);
		model.addAttribute("ok", "Yet");

		return "write_food";
	}

	/**
	 * Create a board including food pictures and tags... 
	 *
	 * @param  fuf	including food names, food pictures and tags.
	 * @param  user	a current authorized User  
	 * @return      redirect a detailed board view
	 * @throws IllegaStateExecption, IOException 
	 * @see        BoardDetail
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN') AND hasAuthority('ROLE_USER')")
	@PostMapping("/upload")
	public String saveFile(@ModelAttribute(value = "fileUpload") FileUploadForm fuf,
			@ModelAttribute("customUser") CustomUser user, Model model) throws IllegalStateException, IOException {

		String filename = fuf.getFiles().get(0).getOriginalFilename();

		model.addAttribute("ok", filename);

		logger.info("Files : " + fuf.getFiles().size());

		fuf.getFoodList().forEach(f -> logger.info(f));

		Board b = new Board();

		b.setOwner(user.getNickName());

		boardService.save(b, fuf);

		return "redirect:/matches/" + b.getId();
	}

	
	/**
	 * Return thumbnail list.
	 *
	 * @param  model	including food names, food pictures and tags.
	 * @param  startNum	a current authorized User
	 * @param  offset	Will show board numbers  
	 * @return List<Board>	Return board list by json form  
	 */
	@GetMapping("/matches")
	public String matchList(Model model,@RequestParam(value="startNum",required = false, defaultValue="0") Integer startNum,
			@RequestParam(value="offset",required = false, defaultValue="3") int offset) {
		logger.info("startNum :" +startNum);
		logger.info("offset :"+offset);
		
		Integer start = startNum;
		offset = offset == 0 ? this.pageOffset : offset;
		
		// board의 total number를 확
		int total = 100;
		
		
		/*
		 * 서버에 있는 데이터에 따라 쿼리를 다르게 한다.
		 */		
		
		if(total > start){
			//model.addAttribute("boardlist", boardList);
			logger.info("StartNum : "+startNum);
			List<ThumbNail> thumbNailList = boardService.findAll(start,offset);

			logger.info("board num : "+thumbNailList.size());
			model.addAttribute("startNum",startNum+offset);
			model.addAttribute("thumbNailList",thumbNailList);
		}
		
		
	
		return "food_content";
	}

	/**
	 * Return {id} detail board view
	 * 
	 * @param model Including board info such as board, food images and replies
	 * @param id	a board id
	 * @return String	Return a board detail view
	 */
	@GetMapping("/matches/{id}")
	public String matchDetail(Model model, @PathVariable int id) {

		BoardDetail bd = boardService.findById(id);
		model.addAttribute("bd", bd);
		
		logger.info("reply :" + bd.getCommentList().size());
		// 리플 틀 부
		model.addAttribute("reply",new Reply());
		// 다린 리플 추가 예
		
		return "match_info";
	}

	
	/**
	 * Update {id} board info and return the updated board detail view. 
	 * 
	 * @param id	updating board id
	 * @param fuf	An object updating a board content, food name or food images
	 * @return redirect the view by updated {id} board 
	 */
	@PostMapping("/matches/{id}/update")
	public String updateMatch(@PathVariable String id, FileUploadForm fuf) {
		return "redirect:/matches/" + id;
	}
	
	
	@RequestMapping(value="/matches/{id}/reply", method = RequestMethod.POST)
	public String saveReply(@PathVariable("id")int boardId, @RequestParam("comment") String comment,@ModelAttribute("customUser") CustomUser user, Model model){
		logger.info("comment:"+ comment);
		Reply r = new Reply();
		r.setComment(comment);
		r.setBoardId(boardId);
		r.setReplyer(user.getNickName());
		boardService.saveReply(r);
		
		model.addAttribute("comment",r);
		
		return "comment";
	}
	
	@GetMapping("/matches/reply/{id}")
	public void updateReply(@PathVariable("id") int replyId, @RequestParam("comment") String comment, @ModelAttribute("customUser") CustomUser user, Model model){
		logger.info("updat comment : " + comment);
		
		
	}
	
	/**
	 * Save 'like' if not exists in a table or delete it.
	 * 
	 * @param id board id for user's like or unlike
	 * @param currentUser login user 
	 * 
	 * @return total like number of the board {id}
	 */
	@GetMapping(value = "/matches/like/{id}")
	@ResponseBody
	public int updateLike(@PathVariable("id") Integer id, @ModelAttribute("customUser") CustomUser user){
		
		Like like = new Like(id,user.getUserEmail());
		
		Like isLike = likeMapper.find(like);
		
		logger.info("like test :" + (isLike==null));
		
		return isLike == null ? likeMapper.save(like) : -likeMapper.delete(like);
	}
	
	
	
	/**
	 * Return a binary image file
	 * 
	 * @param fileName a file name which a client requests
	 * @return Image The image file titled fileName 
	 */
	@RequestMapping(value = "/img/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFile(@PathVariable("filename") String fileName) {
		logger.info("FILE ID: " +fileName);
		byte[] media = FileUtil.getFile(fileName);
		HttpHeaders headers = new HttpHeaders();
		
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
		return responseEntity;

	}

}
