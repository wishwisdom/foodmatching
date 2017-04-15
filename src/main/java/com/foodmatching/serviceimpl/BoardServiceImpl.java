package com.foodmatching.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.foodmatching.mapper.BoardMapper;
import com.foodmatching.mapper.FoodMapper;
import com.foodmatching.mapper.ReplyMapper;
import com.foodmatching.model.Board;
import com.foodmatching.model.BoardDetail;
import com.foodmatching.model.Comment;
import com.foodmatching.model.FileUploadForm;
import com.foodmatching.model.Food;
import com.foodmatching.model.Reply;
import com.foodmatching.service.BoardService;
import com.foodmatching.utils.FileUtil;

@Service
public class BoardServiceImpl implements BoardService{
	private final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	/*
	 * (non-Javadoc)
	 * @see com.foodmatching.service.Service#find(java.lang.String)
	 * @description
	 * 	 하나의 게시물을 보는 것.
	 * 	 ->구체적인 사진이나 내용들이 보여줘야 한다. 
	 */
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FoodMapper foodMapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	/**
	 * Returns a BoardDetail which includes a board, food images related the board, replies. 
	 *
	 * @param  id  Board id
	 * @return bd  Return a BoardDetail which is related with foods and replies.     
	 * @see        BoardDetail
	 */
	public BoardDetail findById(int id){
		BoardDetail bd = boardMapper.findById(id);
		List<Comment> replyList = this.findReplies(id);
		
		bd.setCommentList(replyList);
		
		return bd;
	}
	
	
	@Override
	public Board find(String id) {
		
		//id를 통해 게시물 가져오기
			// 연관된 사진
			// 연관된 댓글
			// 연관된 음식...
		
		Board b = boardMapper.find(Integer.parseInt(id));
		
		
		return b;
	}
	
	/**
	 * Return Boards startNum ~ startNum+offset(to last board)
	 * 
	 * @param startNum	start board number
	 * @param offset	how many get pages
	 * @return List<Board> return boards between startNum and startNum+offset
	 * 
	 */
	public List<Board> findAll(Integer startNum, int offset) {
		// TODO Auto-generated method stub
		List<Board> list = null;
		// total number 가져오기
		int totalNum = boardMapper.countTotalRow();
		// 페이징 처리하기
		if(startNum < totalNum){
			
			 list = boardMapper.findAll(startNum,offset);
			
		}else
			list = new ArrayList<Board>();
				
		return list;
	}
	
	/**
	 * Save a board and food images
	 * 
	 * @param b board including board name, writer and title.
	 * @param fuf food images for saving.
	 * @reutrn void
	 */
	
	@Transactional
	public void save(Board b, FileUploadForm fuf){
		List<Food> foodList = new ArrayList<Food>();
		Map<String,MultipartFile> fileMap = new HashMap<>();
		
		int fileSize = fuf.getFiles().size();
		for(int i=0; i < fileSize; i++){			
			MultipartFile m = fuf.getFiles().get(i);
			String destinationFileName = FileUtil.extractDestinationFileName(m);
			
			Food f = new Food();
			
			f.setFoodName(fuf.getFoodList().get(i));
			
			f.setLocation("test");
			
			f.setFoodImage(destinationFileName);
			
			foodList.add(f);
			
			fileMap.put(f.getFoodImage(), m);
		}
		// Insert Board
		save(b);
		
		// Yet saveAll Food
		foodList.forEach(f->{
			f.setBoardId(b.getId());
			foodMapper.save(f);
		});
		
		
		// save Files
		FileUtil.saveFileMap(fileMap);
		
	}
	
	/**
	 * Save a reply and return it setting its id.
	 * @param r Inclduing a comment object
	 * @return r After save a reply, return it include id.
	 * 
	 * @see Reply
	 */
	public Reply saveReply(Reply r){
		replyMapper.save(r);
		return r;
	}
	
	
	/**
	 * Returns a Comment which includes a board, food images related the board, replies. 
	 *
	 * @param  id  Board id
	 * @return commentList  Return Comments which include parent a reply and the parent's children.     
	 * @see        Comment
	 */
	public List<Comment> findReplies(int boardId){
		List<Reply> list = replyMapper.findReply(boardId);
		List<Comment> commentList = new ArrayList<Comment>();
		logger.info("findReplies : "+boardId);
		
		list.forEach(r->{
			Comment m = new Comment();
			List<Reply> replyList = replyMapper.findChildren(r.getReId());
			
			m.setReply(r);
			m.setReplyList(replyList);
			
			commentList.add(m);
		});
		
		return commentList;
	}
	

	@Override
	public int save(Board b) {
		return boardMapper.save(b);
	}
	

	@Override
	public int saveAll(List<Board> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Board t) {
		// TODO Auto-generated method stub
		return boardMapper.delete(t);
	}

	@Override
	public int deleteAll(List<Board> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
