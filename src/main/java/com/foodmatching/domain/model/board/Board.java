package com.foodmatching.domain.model.board;

import java.sql.Timestamp;
import java.util.List;

import com.foodmatching.domain.model.Food;
import com.foodmatching.domain.model.Reply;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@NoArgsConstructor

@Getter
@Entity
public class Board {
	private final static Logger logger = LoggerFactory.getLogger(Board.class.getName());

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String summary;
	private String owner;
	private int likes;
	private Timestamp createdDate;

	@OneToMany(mappedBy = "board")
    private List<Food> foods;

	@OneToMany(mappedBy = "board")
    private List<Reply> replies;
	
	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Return String whitch is diff times between current and created time
	 *  If diff time is so great number, the calucation is not corrected.
	 *   
	 * @return difference between current and created time.
	 */
	public String getTime(){
		String strCreatedTime = "";
		long diff = System.currentTimeMillis()-this.createdDate.getTime();
		
		int minutes =(int)((diff/(1000*60)));
		
		int years = (int)(minutes/(60*24*365));
		if(years > 0 ){
			strCreatedTime = years + " 년 ";
		}else{
			int month = (int)(minutes/(60*24*(365/12)));
			if(month > 0){
				strCreatedTime = month + "달 전";
			}else{
				int days = (int)(minutes/(60*24));
				if(days > 0){
					strCreatedTime += days +"일 전";
				}else{
					int hours = (int)(minutes/60);
					
					if(hours > 0){
						strCreatedTime = hours +"시간 전";
					}else{
						if(minutes > 0){
							strCreatedTime = minutes + "분 전";
						}else{
							strCreatedTime = "방금 전";
						}
					}
				}
			}
		}
					
			
		
		
		return strCreatedTime;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

    public String getTitle(){
        String title = "";

        int size = foods.size();

        for(int i=0; i < size-1; i++ ){
            title += foods.get(i).getFoodName()+" & ";
        }

        title += foods.get(size-1).getFoodName();

        return title;
    }
}