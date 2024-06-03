package com.mydiary.my_diary_server.test2;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CalendarInfo {
	private List<Diary> diaries;


}
