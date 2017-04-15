package com.foodmatching.model;

import java.util.List;

public class Comment {
	Reply reply;
	List<Reply> replyList;
	
	public Reply getReply() {
		return reply;
	}
	public void setReply(Reply reply) {
		this.reply = reply;
	}
	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
}
