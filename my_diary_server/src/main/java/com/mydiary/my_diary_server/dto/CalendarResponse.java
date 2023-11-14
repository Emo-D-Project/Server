package com.mydiary.my_diary_server.dto;
import lombok.*;

@Setter
@Getter
public class CalendarResponse {
	Long id;
	Integer day;
	String emotion;
	
	public CalendarResponse(Long id, Integer day, String emotion)
	{
		this.id = id;
		this.day = day;
		this.emotion = emotion;
		System.out.println(emotion);
	}
	
	public CalendarResponse(CalendarResponse res)
	{
		this.id = res.getId();
		this.day = res.getDay();
		this.emotion = res.getEmotion();
	}
}
