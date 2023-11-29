package com.mydiary.my_diary_server.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Column
    private String audio; // 저장된 audio의 url

    @ElementCollection
    @Column(name = "images", length = 3) // 최대 3장의 이미지를 저장할 수 있도록 길이 지정
    private List<String> images;

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
    	this.CreatedAt = LocalDateTime.now().minusMonths(1).minusDays(13);
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

    public void addImage(String imageData) {
        if (images == null) {
            images = new ArrayList<>();
        }
        if (images.size() < 3) {
            images.add(imageData);
        } else {
            // 이미지가 최대 3장을 초과하면 예외 처리 또는 추가 로직을 수행할 수 있습니다.
            throw new RuntimeException("최대 3장의 이미지를 초과했습니다.");
        }
    }

    public List<String> getImages() {
        return images != null ? new ArrayList<>(images) : Collections.emptyList();
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}

