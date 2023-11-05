package com.mydiary.my_diary_server.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "diaries")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
       
    private String content;
    private Integer empathy;
    private String emotion;
    private Boolean is_comm;
    private Boolean is_share;
    private long user_id;


    @CreatedDate
    @Column(name = "created_at")
    public LocalDateTime CreatedAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
    
    @Builder
    public Diary(long user_id, String emotion, String content, Boolean is_share, Boolean is_comm){
    	this.user_id = user_id;
    	this.emotion = emotion;
    	this.content = content;
    	this.is_comm = is_share;
    	this.is_share = is_comm;
    }

    public void update(String emotion, String content, Boolean is_share, Boolean is_comm) {
        this.emotion = emotion;
        this.content = content;
        this.is_share = is_share;
        this.is_comm = is_comm;
    }

    
}

