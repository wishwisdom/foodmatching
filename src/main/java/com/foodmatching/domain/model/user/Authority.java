package com.foodmatching.domain.model.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by shin on 2017. 9. 10..
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private UserRole authority;

    public Authority(User user,UserRole role){
        this.user = user;
        this.authority = role;
    }

}
