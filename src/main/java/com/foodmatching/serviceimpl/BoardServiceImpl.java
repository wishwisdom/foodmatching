package com.foodmatching.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.foodmatching.mapper.BoardMapper;
import com.foodmatching.mapper.FoodMapper;
import com.foodmatching.mapper.ReplyMapper;
import com.foodmatching.model.Board;
import com.foodmatching.model.BoardDetail;
import com.foodmatching.model.BoardForm;
import com.foodmatching.model.Comment;
import com.foodmatching.model.FileUploadForm;
import com.foodmatching.model.Food;
import com.foodmatching.model.Like;
import com.foodmatching.model.Reply;
import com.foodmatching.model.ThumbNail;
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
	
	@Value("${board.img.path}")
	public String SAVE_PATH;
	
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
	 * @return List<ThumbNail> return boards between startNum and startNum+offset
	 * 
	 */
	public List<ThumbNail> findAll(Integer startNum, int offset) {
		// TODO Auto-generated method stub
		List<Board> list = null;
		List<ThumbNail> thumNailList = new ArrayList<>();
		// total number 가져오기
		int totalNum = boardMapper.countTotalRow();
		// 페이징 처리하기
		if(startNum < totalNum){
			
			 list = boardMapper.findAll(startNum,offset);
			 
			 // DB접근이 2번이상 발생함.향후 SQL 수정이 불가피함.
			 
			 list.forEach(b->{
				 ThumbNail t = new ThumbNail(b);
				 t.setFood(foodMapper.find(b.getId()));
				 
				 thumNailList.add(t);
			 });
			
		}
		
		return thumNailList;
	}
	
	/**
	 * Save a board and food images
	 * 
	 * @param b board including board name, writer and title.
	 * @param fuf food images for saving.
	 * @reutrn void
	 */
	
	@Transactional
	public void save(Board b, BoardForm bf){
		List<Food> foodList = new ArrayList<Food>();
		Map<String,MultipartFile> fileMap = new HashMap<>();
		
		
		int foodNum = bf.getFoodNames().length;
		String destinationFileName = "";
		String foodImageName = "";
		for(int i=0; i < foodNum; i++){			
			Food f = new Food();
			
			f.setFoodName(bf.getFoodNames()[i]);
			
			if(bf.getPictures().size() > i){
				MultipartFile m = bf.getPictures().get(i);
				foodImageName=bf.getFoodImageNames().get(i);
				String[] nameSplites = foodImageName.split("\\.");
				
				
				// length가 0일 경우 문제가 발생함.
				destinationFileName = FileUtil.extractDestinationFileName(m,nameSplites[nameSplites.length-1]);
				
				fileMap.put(destinationFileName, m);
			}
			f.setFoodImage(foodImageName);
			f.setFoodSavedLocation(destinationFileName);
			f.setLocation("test");
			foodList.add(f);
		}
		// Insert Board
		save(b);
		
		// Yet saveAll Food
		foodList.forEach(f->{
			f.setBoardId(b.getId());
			foodMapper.save(f);
		});
		
		
		// save Files
		FileUtil.saveFileMap(SAVE_PATH,fileMap);
		
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
	
	public void updateLike(Like like){
		
		
		
		
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

	public int countTotalNum(){
		return boardMapper.countTotalRow();
	}
	

}
