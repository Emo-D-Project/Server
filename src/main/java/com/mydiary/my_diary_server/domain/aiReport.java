package com.mydiary.my_diary_server.domain;

import java.time.LocalDateTime;
import java.time.YearMonth;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mydiary.my_diary_server.dto.aiReportDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="ai_report")
public class aiReport {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="user_id")
	private Long userId;
	
	private String positive;
	private String negative;
	private String emotion;
	
	private String mon;
	private String tue;
	private String wed;
	private String thur;
	private String fri;
	private String sat;
	private String sun;

	LocalDateTime createdAt;
	
    @Builder
    public aiReport(Long user_id, aiReportDTO dto){
    	this.userId = user_id;
    	this.positive = dto.getPositive();
    	this.negative = dto.getNegative();
    	this.emotion = dto.getEmotion();
    	this.mon = dto.getMon();
    	this.tue = dto.getTue();
    	this.wed = dto.getWed();
    	this.thur = dto.getThu();
    	this.fri = dto.getFri();
    	this.sat = dto.getSat();
    	this.sun = dto.getSun();
    	this.createdAt = LocalDateTime.now();
    }
}
