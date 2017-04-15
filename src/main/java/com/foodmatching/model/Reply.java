/**
 * 
 */
package com.foodmatching.model;

import java.sql.Date;

/**
 * @author shin
 *
 */
public class Reply {
	private int reId;        // 댓글 번호
    private int boardId;        // 게시글 번호
    private String comment;    // 댓글 내용
    private String replyer;        // 댓글 작성자
    private Date regdate;        // 댓글 작성일자
    private Date updatedate;    // 댓글 수정일자
    private int parentId;
    
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	public int getReId() {
		return reId;
	}
	public void setReId(int reId) {
		this.reId = reId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getReplyer() {
		return replyer;
	}
	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
}
