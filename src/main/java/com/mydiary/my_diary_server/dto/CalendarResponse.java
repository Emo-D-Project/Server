package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
public class CalendarResponse {
	Date date;
	String formattedDate; // 문자열로 변환된 날짜를 저장하는 추가 필드
	String emotion;

	public CalendarResponse(Diary diary) {
		this.date = Date.from(diary.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant());

		// SimpleDateFormat을 사용하여 Date를 원하는 형식의 문자열로 변환
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.formattedDate = dateFormat.format(this.date);

		this.emotion = diary.getEmotion();
	}
}
