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
    
    @Column(name = "user_id")
    private Long userId;
    private String author;
    
    @CreatedDate
    @Column(name = "created_at")
    public LocalDateTime CreatedAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
    
    @Builder
    public Diary(Long user_id, String content, String emotion, Boolean is_share, Boolean is_comm){
    	this.userId = user_id;
    	this.author = Long.toString(user_id);
    	this.empathy = 0;
    	this.emotion = emotion;
    	this.content = content;
    	this.is_comm = is_share;
    	this.is_share = is_comm;
    	this.CreatedAt = LocalDateTime.now();
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

