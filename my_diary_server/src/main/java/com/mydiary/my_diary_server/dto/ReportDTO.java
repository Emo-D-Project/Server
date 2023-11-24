package com.mydiary.my_diary_server.dto;

import java.time.LocalDateTime;
import java.time.YearMonth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDTO {
	YearMonth date;
	String mostEmotion;
	String leastEmotion;
	Integer[] emotionNums;
	String comment;
	Integer score;
}
