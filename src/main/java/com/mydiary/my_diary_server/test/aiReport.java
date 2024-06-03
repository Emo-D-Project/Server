package com.mydiary.my_diary_server.test;

import com.mydiary.my_diary_server.dto.aiReportDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

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
