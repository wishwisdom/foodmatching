package com.foodmatching.domain.model.user;

import com.foodmatching.domain.model.Scrap;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by shin on 2017. 11. 28..
 */
@Embeddable
public class Pocket {
    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Scrap> scraps;

    public void add(Scrap s){
        this.scraps.add(s);
    }
    public void remove(Scrap s){
        for(Scrap origin : scraps){
            if(origin.getId() == s.getId()){
                scraps.remove(origin);
                break;
            }
        }
    }
}
