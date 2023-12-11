package com.mydiary.my_diary_server.dto;

import java.time.LocalDateTime;
import java.time.YearMonth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDTO {
	YearMonth date;
	Integer mostEmotion;
	Integer leastEmotion;
	Integer[] emotionNums;
	String comment;
	Integer score;
}
