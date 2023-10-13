package com.mydiary.my_diary_server.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diaries")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate date;

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

