package com.mydiary.my_diary_server.test;

import com.mydiary.my_diary_server.domain.Report;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReportResponse {
	public LocalDate date;
	public int point;

	public Integer mostEmotion;
	public Integer leastEmotion;
	public String comment;

	public Double[] emotions = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	
	public ReportResponse(Report report)
	{
		this.date = LocalDate.of(report.getYear(), report.getMonth(), 1);
		this.point = report.getPoint();
		this.mostEmotion = report.getMostEmotion();
		this.leastEmotion = report.getLeastEmotion();
		this.mostEmotion = report.getMostEmotion();
		this.comment = report.getComment();
		this.emotions[0] = Double.valueOf(report.getSmile());
		this.emotions[1] = Double.valueOf(report.getFlutter());
		this.emotions[2] = Double.valueOf(report.getAngry());
		this.emotions[3] = Double.valueOf(report.getAnnoying());
		this.emotions[4] = Double.valueOf(report.getTired());
		this.emotions[5] = Double.valueOf(report.getSad());
		this.emotions[6]= Double.valueOf(report.getCalmness());
	}
}
