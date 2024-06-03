package com.mydiary.my_diary_server.test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Column(name = "user_id")
    private Long userId;
    private String author;
    
    @CreatedDate
    @Column(name = "created_at")
    public LocalDateTime CreatedAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    private String audio;
    private String image1;
    private String image2;
    private String image3;

    
    @Builder
    public Diary(Long user_id, String content, String emotion, Boolean is_share, Boolean is_comm){
    	this.userId = user_id;
    	this.author = Long.toString(user_id);
    	this.empathy = 0;
    	this.emotion = emotion;
    	this.content = content;
    	this.is_comm = is_share;
    	this.is_share = is_comm;
    	this.CreatedAt = LocalDateTime.now().plusHours(9);
    }

    @Builder
    public Diary(Long user_id, String content, String emotion, Boolean is_share, Boolean is_comm, int day){
    	this.userId = user_id;
    	this.author = Long.toString(user_id);
    	this.empathy = 0;
    	this.emotion = emotion;
    	this.content = content;
    	this.is_comm = is_share;
    	this.is_share = is_comm;
    	this.CreatedAt = LocalDateTime.now().minusDays(day);
    }
    
    public void update(String emotion, String content, Boolean is_share, Boolean is_comm) {
        this.emotion = emotion;
        this.content = content;
        this.is_share = is_share;
        this.is_comm = is_comm;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void recommend(Boolean is_re)
    {
    	if(is_re == true)
    		this.empathy += 1;
    	else
    		this.empathy -= 1;
    }

}