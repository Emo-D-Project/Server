package com.mydiary.my_diary_server.test;

import com.mydiary.my_diary_server.dto.ReportDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="report")
public class Report {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="user_id")
	private Long userId;
	

	private int month;
	private int year;
	private int point;

	private Integer mostEmotion;
	private Integer leastEmotion;
	private String comment;

	private int smile;
	private int flutter;
	private int angry;
	private int annoying;
	private int tired;
	private int sad;
	private int calmness;
	
	@Builder
	public Report(Long user_id, ReportDTO dto)
	{
		this.userId = user_id;
		this.year = dto.getDate().getYear();
		this.month = dto.getDate().getMonthValue();
		this.point = dto.getScore();
		this.mostEmotion = dto.getMostEmotion();
		this.leastEmotion = dto.getLeastEmotion();
		this.comment = dto.getComment();
		this.smile = dto.getEmotionNums()[0];
		this.flutter = dto.getEmotionNums()[1];
		this.angry = dto.getEmotionNums()[2];
		this.annoying = dto.getEmotionNums()[3];
		this.tired = dto.getEmotionNums()[4];
		this.sad = dto.getEmotionNums()[5];
		this.calmness = dto.getEmotionNums()[6];
	}
	
	
}
