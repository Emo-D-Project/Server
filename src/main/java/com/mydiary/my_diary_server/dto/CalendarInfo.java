package com.mydiary.my_diary_server.dto;

import java.util.List;

import com.mydiary.my_diary_server.domain.Diary;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CalendarInfo {
	private List<Diary> diaries;


}
