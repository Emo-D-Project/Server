package com.mydiary.my_diary_server.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mydiary.my_diary_server.domain.Report;

import lombok.Getter;

@Getter
public class ReportResponse {
	public LocalDate date;
	public int point;

	public Integer mostEmotion;
	public Integer leastEmotion;
	public String comment;

	public Integer[] emotions = {0, 0, 0, 0, 0, 0, 0};
	
	public ReportResponse(Report report)
	{
		this.date = LocalDate.of(report.getYear(), report.getMonth(), 1);
		this.point = report.getPoint();
		this.mostEmotion = report.getMostEmotion();
		this.leastEmotion = report.getLeastEmotion();
		this.mostEmotion = report.getMostEmotion();
		this.comment = report.getComment();
		this.emotions[0] = report.getSmile();
		this.emotions[1] = report.getFlutter();
		this.emotions[2] = report.getAngry();
		this.emotions[3] = report.getAnnoying();
		this.emotions[4] = report.getTired();
		this.emotions[5] = report.getSad();
		this.emotions[6]= report.getCalmness();
	}
}
