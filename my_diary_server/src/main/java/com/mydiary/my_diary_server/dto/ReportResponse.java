package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Report;

import lombok.Getter;

@Getter
public class ReportResponse {
	public int month;
	public int year;
	public int point;

	public String mostEmotion;
	public String leastEmotion;
	public String comment;

	public int smile;
	public int flutter;
	public int angry;
	public int annoying;
	public int tired;
	public int sad;
	public int calmness;
	
	public ReportResponse(Report report)
	{
		this.month = report.getMonth();
		this.year = report.getYear();
		this.point = report.getPoint();
		this.mostEmotion = report.getMostEmotion();
		this.leastEmotion = report.getLeastEmotion();
		this.comment = report.getComment();
		this.smile = report.getSmile();
		this.flutter = report.getFlutter();
		this.angry = report.getAngry();
		this.annoying = report.getAnnoying();
		this.tired = report.getTired();
		this.sad = report.getSad();
		this.calmness = report.getCalmness();
	}
}
