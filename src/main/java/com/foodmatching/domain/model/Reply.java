/**
 * 
 */
package com.foodmatching.domain.model;

import com.foodmatching.domain.model.board.Board;
import com.foodmatching.domain.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author shin
 *
 */
@NoArgsConstructor
@Getter
@Entity
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;        // 댓글 번호

	@ManyToOne
    private Board board;        // 게시글 번호
    private String comment;    // 댓글 내용

    @ManyToOne
    private User user;        // 댓글 작성자
    private LocalDateTime regdate;        // 댓글 작성일자
    private LocalDateTime updatedate;    // 댓글 수정일자

    public Reply(Board board, String comment, User user){
        this.board = board;
        this.comment = comment;
        this.user = user;

        this.regdate = LocalDateTime.now();
        this.updatedate = LocalDateTime.now();
    }

    public void setUpdatedate(){
        this.updatedate = LocalDateTime.now();
    }


}
