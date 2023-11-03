package com.mydiary.my_diary_server.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userr_id")
    private User user;

    @Column
    private String music;

    @Column
    private String hobby;

    @Column
    private String mbti;

    public void update(UserInfo userInfo) {
        this.music = userInfo.getMusic();
        this.hobby = userInfo.getHobby();
        this.mbti = userInfo.getMbti();
    }
}
