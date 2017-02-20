package com.foodmatching.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodmatching.mapper.BoardMapper;
import com.foodmatching.model.Board;
import com.foodmatching.service.BoardService;

@Service
public class BoardServiceImp implements BoardService{

	/*
	 * (non-Javadoc)
	 * @see com.foodmatching.service.Service#find(java.lang.String)
	 * @description
	 * 	 하나의 게시물을 보는 것.
	 * 	 ->구체적인 사진이나 내용들이 보여줘야 한다. 
	 */
	@Autowired
	private BoardMapper boardMapper;
	
	
	@Override
	public Board find(String id) {
		
		//id를 통해 게시물 가져오기
			// 연관된 사진
			// 연관된 댓글
			// 연관된 음식...
		
		Board b = boardMapper.findBoard(Integer.parseInt(id));
		
		
		
		return b;
	}
	/*
	 * 
	 * @see com.foodmatching.service.Service#findAll(java.util.List)
	 * @description
	 * 	페이징 처리를 하지 않음... 이후 페이징처리를 위한 것을 해야 됨.
	 */

//	@Override
//	public List<Board> findAll(List<Integer> idList) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public int save(Board b) {
		// TODO Auto-generated method stub
		
		boardMapper.insertBoard(b);
		return 0;
	}

	@Override
	public int saveAll(List<Board> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Board t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAll(List<Board> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
