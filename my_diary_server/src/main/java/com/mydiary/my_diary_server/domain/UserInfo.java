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
    private User userId;

    @Column
    private String title;

    @Column
    private String content;


    public void update(UserInfo userInfo) {
        this.title = userInfo.getTitle();
        this.content = userInfo.getContent();
    }
}
