package com.mydiary.my_diary_server.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.mydiary.my_diary_server.domain.Diary;

import lombok.*;

@Getter
@Setter
public class CalendarInfo {
	private List<Diary> ids;
	private LocalDateTime date;
	
	public CalendarInfo(LocalDateTime date)
	{
		this.date = date;
	}
}
