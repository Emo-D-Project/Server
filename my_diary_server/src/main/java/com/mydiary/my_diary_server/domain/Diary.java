package com.mydiary.my_diary_server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "diaries")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private LocalDate date;

    @Column(name = "author", nullable = false)
    private String author;


    @Builder
    public Diary(String author, String title, String content){
        this.author = author;
        this.title = title;
        this.content = content;
    }

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User author;

    // 다대다 관계: 일기와 태그
//    @ManyToMany
//    @JoinTable(
//            name = "diary_tag",
//            joinColumns = @JoinColumn(name = "diary_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id")
//    )
//    private Set<Tag> tags;

    public LocalDateTime CreatedAt;

    public LocalDateTime updatedAt;

    // Constructors, getters, setters, and other methods


}

