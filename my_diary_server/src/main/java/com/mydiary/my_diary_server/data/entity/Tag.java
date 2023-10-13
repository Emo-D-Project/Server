//package com.mydiary.my_diary_server.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.List;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Tag {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    // 태그와의 관계 설정
//    @ManyToMany(mappedBy = "tags")
//    private List<Diary> diaries;
//}
